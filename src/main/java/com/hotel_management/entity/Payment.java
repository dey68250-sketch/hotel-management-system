package com.hotel_management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String screenshotPath;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToOne
    private Booking booking;
}
