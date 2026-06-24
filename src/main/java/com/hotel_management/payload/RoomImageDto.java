package com.hotel_management.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomImageDto {

    private Long id;
    private String imagePath;
}
