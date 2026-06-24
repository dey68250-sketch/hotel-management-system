package com.hotel_management.payload;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingReceiptResponse {

    private Long bookingId;

    private String guestName;

    private String guestEmail;

    private String guestPhone;

    private String roomType;

    private String roomNumber;

    private Double price;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String paymentStatus;

    private String bookingStatus;
}
