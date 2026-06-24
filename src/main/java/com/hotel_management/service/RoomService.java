package com.hotel_management.service;

import com.hotel_management.payload.RoomRequest;
import com.hotel_management.payload.RoomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    RoomResponse createRoom(RoomRequest request);
    RoomResponse getRoomById(Long id);

    List<RoomResponse> getAllRooms();

    RoomResponse updateRoom(Long id, RoomRequest request);

    void deleteRoom(Long id);

    List<RoomResponse> getAvailableRooms(
            LocalDate checkIn,
            LocalDate checkOut
    );

    void deleteRoomImage(Long imageId);

    void addRoomImage(Long roomId,
                      MultipartFile file);
}
