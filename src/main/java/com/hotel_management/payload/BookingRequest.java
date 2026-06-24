package com.hotel_management.payload;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    private String guestName;
    private String guestEmail;
    private String guestPhone;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private Long roomId;
}