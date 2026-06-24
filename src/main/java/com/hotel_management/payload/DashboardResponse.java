package com.hotel_management.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private Long totalRooms;
    private Long availableRooms;
    private Long bookedRooms;

    private Long totalBookings;
    private Long cancelledBookings;
    private Long confirmedBookings;
}
