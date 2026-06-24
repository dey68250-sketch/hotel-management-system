package com.hotel_management.payload;

import lombok.Data;

@Data
public class RoomRequest {

    private String roomNumber;
    private String roomType;
    private Double price;
    private Integer capacity;
    private Boolean available;
}