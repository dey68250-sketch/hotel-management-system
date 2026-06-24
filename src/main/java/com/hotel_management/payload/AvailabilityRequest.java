package com.hotel_management.payload;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailabilityRequest {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}