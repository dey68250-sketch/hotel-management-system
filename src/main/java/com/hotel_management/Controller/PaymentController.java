package com.hotel_management.Controller;

import com.hotel_management.payload.PaymentInfoResponse;
import com.hotel_management.payload.PaymentResponse;
import com.hotel_management.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{bookingId}/upload")
    public ResponseEntity<String> uploadPayment(
            @PathVariable Long bookingId,
            @RequestParam("file") MultipartFile file) {

        paymentService.uploadPayment(bookingId, file);

        return ResponseEntity.ok(
                "Payment screenshot uploaded successfully"
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PaymentResponse>>
    getPendingPayments() {

        return ResponseEntity.ok(
                paymentService.getPendingPayments()
        );
    }

    @PutMapping("/{paymentId}/verify")
    public ResponseEntity<String> verifyPayment(
            @PathVariable Long paymentId) {

        paymentService.verifyPayment(paymentId);

        return ResponseEntity.ok(
                "Payment verified successfully"
        );
    }

    @GetMapping("/info")
    public ResponseEntity<PaymentInfoResponse> getPaymentInfo() {

        return ResponseEntity.ok(
                paymentService.getPaymentInfo()
        );
    }
}