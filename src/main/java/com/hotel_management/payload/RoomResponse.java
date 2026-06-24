package com.hotel_management.payload;

import com.hotel_management.entity.RoomImage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomResponse {

    private Long id;
    private String roomNumber;
    private String roomType;
    private Double price;
    private Integer capacity;
    private Boolean available;
    private List<RoomImageDto> images;
}
