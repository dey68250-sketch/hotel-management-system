package com.hotel_management.service;

import com.hotel_management.payload.PaymentInfoResponse;
import com.hotel_management.payload.PaymentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PaymentService {

    void uploadPayment(
            Long bookingId,
            MultipartFile file
    );

    void verifyPayment(Long paymentId);
    List<PaymentResponse> getPendingPayments();
    PaymentInfoResponse getPaymentInfo();
}
