package com.hotel_management.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RoomImageResponse {

    private Long id;
    private String imagePath;
}
