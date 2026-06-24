package com.hotel_management.repository;

import com.hotel_management.entity.Booking;
import com.hotel_management.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomId(Long roomId);
    long countByStatus(BookingStatus status);
    @Query("""
       SELECT b.room.id
       FROM Booking b
       WHERE b.status = 'CONFIRMED'
       AND b.checkInDate < :checkOut
       AND b.checkOutDate > :checkIn
       """)
    List<Long> findBookedRoomIds(
            LocalDate checkIn,
            LocalDate checkOut
    );
}