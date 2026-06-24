package com.hotel_management.service.Impl;

import com.hotel_management.entity.Booking;
import com.hotel_management.entity.BookingStatus;
import com.hotel_management.entity.Room;
import com.hotel_management.exception.ResourceNotFoundException;
import com.hotel_management.payload.BookingReceiptResponse;
import com.hotel_management.payload.BookingRequest;
import com.hotel_management.payload.BookingResponse;
import com.hotel_management.repository.BookingRepository;
import com.hotel_management.repository.RoomRepository;
import com.hotel_management.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request) {

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));
        Booking booking = new Booking();
        booking.setGuestName(request.getGuestName());
        booking.setGuestEmail(request.getGuestEmail());
        booking.setGuestPhone(request.getGuestPhone());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setStatus(BookingStatus.PENDING);
        booking.setRoom(room);

        List<Booking> existingBookings =
                bookingRepository.findByRoomId(request.getRoomId());

        for (Booking existingBooking : existingBookings) {

            if (existingBooking.getStatus() == BookingStatus.CANCELLED
                    || existingBooking.getStatus() == BookingStatus.COMPLETED) {
                continue;
            }

            boolean overlap =
                    request.getCheckInDate().isBefore(existingBooking.getCheckOutDate())
                            &&
                            request.getCheckOutDate().isAfter(existingBooking.getCheckInDate());

            if (overlap) {
                throw new RuntimeException(
                        "Room is already booked for selected dates");
            }
        }

        Booking savedBooking = bookingRepository.save(booking);

        return BookingResponse.builder()
                .id(savedBooking.getId())
                .guestName(savedBooking.getGuestName())
                .guestEmail(savedBooking.getGuestEmail())
                .guestPhone(savedBooking.getGuestPhone())
                .checkInDate(savedBooking.getCheckInDate())
                .checkOutDate(savedBooking.getCheckOutDate())
                .status(savedBooking.getStatus())
                .roomId(room.getId())
                .build();
    }

    @Override
    public List<BookingResponse> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookingResponse getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        return mapToResponse(booking);
    }

    @Override
    public void deleteBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        bookingRepository.delete(booking);
    }

    @Override
    public BookingResponse cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);

        // ← ADD THIS: free the room when booking is cancelled
        Room room = booking.getRoom();
        room.setAvailable(true);
        roomRepository.save(room);

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Override
    public void checkout(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.COMPLETED);

        Room room = booking.getRoom();

        room.setAvailable(true);

        roomRepository.save(room);
        bookingRepository.save(booking);
    }

    private BookingResponse mapToResponse(Booking booking) {

        return BookingResponse.builder()
                .id(booking.getId())
                .guestName(booking.getGuestName())
                .guestEmail(booking.getGuestEmail())
                .guestPhone(booking.getGuestPhone())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .status(booking.getStatus())
                .roomId(booking.getRoom().getId())
                .build();
    }

    @Override
    public List<BookingResponse> getBookingHistory() {

        List<Booking> bookings =
                bookingRepository.findAll();

        return bookings.stream()
                .map(booking ->
                        BookingResponse.builder()
                                .id(booking.getId())
                                .guestName(booking.getGuestName())
                                .guestEmail(booking.getGuestEmail())
                                .guestPhone(booking.getGuestPhone())
                                .checkInDate(booking.getCheckInDate())
                                .checkOutDate(booking.getCheckOutDate())
                                .status(booking.getStatus())
                                .roomId(booking.getRoom().getId())
                                .build()
                )
                .toList();
    }


    @Override
    public BookingReceiptResponse getReceipt(
            Long bookingId) {

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Booking not found"));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {

            throw new RuntimeException(
                    "Receipt not available until payment is verified"
            );
        }

        Room room = booking.getRoom();

        return BookingReceiptResponse.builder()
                .bookingId(booking.getId())
                .guestName(booking.getGuestName())
                .guestEmail(booking.getGuestEmail())
                .guestPhone(booking.getGuestPhone())
                .roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber())
                .price(room.getPrice())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .bookingStatus(booking.getStatus().name())
                .paymentStatus("VERIFIED")
                .build();
    }

}
