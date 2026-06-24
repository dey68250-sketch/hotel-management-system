package com.hotel_management.service;

import com.hotel_management.payload.BookingReceiptResponse;
import com.hotel_management.payload.BookingRequest;
import com.hotel_management.payload.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    List<BookingResponse> getAllBookings();

    BookingResponse getBookingById(Long id);

    void deleteBooking(Long id);

    BookingResponse cancelBooking(Long id);

    void checkout(Long bookingId);

    List<BookingResponse> getBookingHistory();

    BookingReceiptResponse getReceipt(Long bookingId);
}
