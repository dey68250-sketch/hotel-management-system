package com.hotel_management.service.Impl;

import com.hotel_management.entity.Room;
import com.hotel_management.entity.RoomImage;
import com.hotel_management.exception.ResourceNotFoundException;
import com.hotel_management.payload.RoomImageDto;
import com.hotel_management.payload.RoomRequest;
import com.hotel_management.payload.RoomResponse;
import com.hotel_management.repository.BookingRepository;
import com.hotel_management.repository.RoomImageRepository;
import com.hotel_management.repository.RoomRepository;
import com.hotel_management.service.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomImageRepository roomImageRepository;
    private final RoomRepository roomRepository;
    private List<String> imagePath;
    private final BookingRepository bookingRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;

    public RoomServiceImpl(RoomImageRepository roomImageRepository, RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomImageRepository = roomImageRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public RoomResponse createRoom(RoomRequest request) {

        Room room = new Room();

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPrice(request.getPrice());
        room.setCapacity(request.getCapacity());
        room.setAvailable(request.getAvailable());

        Room savedRoom = roomRepository.save(room);

        return RoomResponse.builder()
                .id(savedRoom.getId())
                .roomNumber(savedRoom.getRoomNumber())
                .roomType(savedRoom.getRoomType())
                .price(savedRoom.getPrice())
                .capacity(savedRoom.getCapacity())
                .available(savedRoom.getAvailable())
                .build();
    }

    @Override
    public RoomResponse getRoomById(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        return mapToResponse(room);
    }

    @Override
    public List<RoomResponse> getAllRooms() {

        return roomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RoomResponse updateRoom(Long id, RoomRequest request) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Room not found"));
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setPrice(request.getPrice());
        room.setCapacity(request.getCapacity());
        room.setAvailable(request.getAvailable());

        Room updatedRoom = roomRepository.save(room);

        return mapToResponse(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        roomRepository.delete(room);
    }

    @Override
    public List<RoomResponse> getAvailableRooms(
            LocalDate checkIn,
            LocalDate checkOut) {

        List<Long> bookedRoomIds =
                bookingRepository.findBookedRoomIds(
                        checkIn,
                        checkOut
                );

        List<Room> rooms;

        if (bookedRoomIds.isEmpty()) {
            rooms = roomRepository.findAll();
        } else {
            rooms = roomRepository
                    .findByIdNotIn(bookedRoomIds);
        }

        return rooms.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteRoomImage(Long imageId) {
        RoomImage image = roomImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        // Detach from parent room first, then delete
        Room room = image.getRoom();
        room.getImages().remove(image);
        roomRepository.save(room);  // ← this triggers orphan removal if @OneToMany has orphanRemoval=true

        roomImageRepository.deleteById(imageId); // ← force delete anyway
    }


    @Override
    public void addRoomImage(Long roomId, MultipartFile file) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        try {
            String uploadPath = System.getProperty("user.dir")
                    + File.separator + uploadDir
                    + File.separator + "rooms";

            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destFile = new File(directory, fileName);
            file.transferTo(destFile.getAbsoluteFile()); // ← key fix

            RoomImage roomImage = new RoomImage();
            // Store only the relative path for consistent URL generation
            roomImage.setImagePath("rooms/" + fileName);
            roomImage.setRoom(room);
            roomImageRepository.save(roomImage);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    private RoomResponse mapToResponse(Room room) {

        List<RoomImageDto> images =
                room.getImages()
                        .stream()
                        .map(image ->
                                RoomImageDto.builder()
                                        .id(image.getId())
                                        .imagePath(image.getImagePath())
                                        .build()
                        )
                        .toList();

        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .price(room.getPrice())
                .capacity(room.getCapacity())
                .available(room.getAvailable())
                .images(images)
                .build();
    }
}