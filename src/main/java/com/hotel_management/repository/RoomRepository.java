package com.hotel_management.repository;


import com.hotel_management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    long countByAvailableTrue();

    long countByAvailableFalse();

    List<Room> findByIdNotIn(List<Long> ids);
}