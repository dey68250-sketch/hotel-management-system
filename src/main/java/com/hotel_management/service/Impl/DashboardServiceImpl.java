package com.hotel_management.service.Impl;

import com.hotel_management.entity.BookingStatus;
import com.hotel_management.payload.DashboardResponse;
import com.hotel_management.repository.BookingRepository;
import com.hotel_management.repository.RoomRepository;
import com.hotel_management.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Override
    public DashboardResponse getDashboardData() {

        long totalRooms = roomRepository.count();

        long availableRooms =
                roomRepository.countByAvailableTrue();

        long bookedRooms =
                roomRepository.countByAvailableFalse();

        long totalBookings =
                bookingRepository.count();

        long cancelledBookings =
                bookingRepository.countByStatus(
                        BookingStatus.CANCELLED);

        long confirmedBookings =
                bookingRepository.countByStatus(
                        BookingStatus.CONFIRMED);

        return DashboardResponse.builder()
                .totalRooms(totalRooms)
                .availableRooms(availableRooms)
                .bookedRooms(bookedRooms)
                .totalBookings(totalBookings)
                .cancelledBookings(cancelledBookings)
                .confirmedBookings(confirmedBookings)
                .build();
    }
}
