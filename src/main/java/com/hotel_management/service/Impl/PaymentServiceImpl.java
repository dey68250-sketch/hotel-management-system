package com.hotel_management.service.Impl;

import com.hotel_management.entity.Booking;
import com.hotel_management.entity.BookingStatus;
import com.hotel_management.entity.Payment;
import com.hotel_management.entity.Room;
import com.hotel_management.exception.ResourceNotFoundException;
import com.hotel_management.payload.PaymentInfoResponse;
import com.hotel_management.payload.PaymentResponse;
import com.hotel_management.repository.BookingRepository;
import com.hotel_management.repository.PaymentRepository;
import com.hotel_management.repository.RoomRepository;
import com.hotel_management.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final RoomRepository roomRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${upi.id}")
    private String upiId;

    @Value("${upi.name}")
    private String upiName;

    @Override
    public void uploadPayment(
            Long bookingId,
            MultipartFile file) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        try {

            String paymentDir =
                    System.getProperty("user.dir")
                            + File.separator
                            + uploadDir
                            + File.separator
                            + "payments"
                            + File.separator;

            File directory = new File(paymentDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            File destinationFile =
                    new File(directory, fileName);

            file.transferTo(destinationFile);

            Payment payment = new Payment();

            payment.setBooking(booking);

            // URL that frontend/admin can access
            payment.setScreenshotPath(
                    "/uploads/payments/" + fileName
            );

            payment.setStatus(
                    BookingStatus.PENDING
            );

            paymentRepository.save(payment);

        } catch (IOException e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to upload payment screenshot: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public void verifyPayment(Long paymentId) {

        Payment payment =
                paymentRepository.findById(paymentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Payment not found"));

        payment.setStatus(BookingStatus.CONFIRMED);

        Booking booking = payment.getBooking();

        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);
        paymentRepository.save(payment);

        Room room = booking.getRoom();
        room.setAvailable(false);

        roomRepository.save(room);
        bookingRepository.save(booking);
        paymentRepository.save(payment);
    }

    @Override
    public List<PaymentResponse> getPendingPayments() {

        List<Payment> payments =
                paymentRepository.findByStatus(
                        BookingStatus.PENDING
                );

        return payments.stream()
                .map(payment ->
                        PaymentResponse.builder()
                                .paymentId(payment.getId())
                                .bookingId(payment.getBooking().getId())
                                .guestName(payment.getBooking().getGuestName())
                                .screenshotPath(payment.getScreenshotPath())
                                .status(payment.getStatus())
                                .build()
                )
                .toList();
    }

    @Override
    public PaymentInfoResponse getPaymentInfo() {

        return PaymentInfoResponse.builder()
                .upiId(upiId)
                .upiName(upiName)
                .qrImageUrl("/uploads/upi/download.jpeg")
                .build();
    }

}