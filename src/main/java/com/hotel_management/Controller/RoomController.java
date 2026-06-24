package com.hotel_management.Controller;

import com.hotel_management.payload.RoomRequest;
import com.hotel_management.payload.RoomResponse;
import com.hotel_management.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})  // ← ADD THIS

public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.createRoom(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(
            @PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable Long id,
            @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponse>>
    getAvailableRooms(

            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut) {

        return ResponseEntity.ok(
                roomService.getAvailableRooms(
                        checkIn,
                        checkOut
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(
            @PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.ok("Room deleted successfully");
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<String>
    deleteRoomImage(
            @PathVariable Long imageId) {

        roomService.deleteRoomImage(imageId);

        return ResponseEntity.ok(
                "Image deleted successfully");
    }

    @PostMapping("/{roomId}/images")
    public ResponseEntity<String>
    addRoomImage(
            @PathVariable Long roomId,
            @RequestParam("file")
            MultipartFile file) {

        roomService.addRoomImage(
                roomId,
                file);

        return ResponseEntity.ok(
                "Image added successfully");
    }
}
