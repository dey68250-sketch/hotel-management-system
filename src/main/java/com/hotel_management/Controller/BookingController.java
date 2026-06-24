package com.hotel_management.Controller;

import com.hotel_management.payload.BookingReceiptResponse;
import com.hotel_management.payload.BookingRequest;
import com.hotel_management.payload.BookingResponse;
import com.hotel_management.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})  // ← ADD THIS

public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody BookingRequest request) {

        return ResponseEntity.ok(
                bookingService.createBooking(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(
            @PathVariable Long id) {

        bookingService.deleteBooking(id);

        return ResponseEntity.ok("Booking deleted successfully");
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }

    @PutMapping("/{bookingId}/checkout")
    public ResponseEntity<String> checkout(
            @PathVariable Long bookingId) {

        bookingService.checkout(bookingId);

        return ResponseEntity.ok(
                "Checkout completed successfully"
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<BookingResponse>>
    getBookingHistory() {

        return ResponseEntity.ok(
                bookingService.getBookingHistory()
        );
    }

    @GetMapping("/{bookingId}/receipt")
    public ResponseEntity<BookingReceiptResponse>
    getReceipt(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                bookingService.getReceipt(
                        bookingId
                )
        );
    }
}
