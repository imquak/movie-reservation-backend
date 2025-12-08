package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Room;
import com.quak.cinema_reservation_app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Get all Rooms
     * @return List of all rooms
     */
    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAll();
    }

    /**
     * Get Room by ID
     * @param id The ID of the room
     * @return The room or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        try {
            Room room = roomService.getById(id);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new Room
     * @param room The room object
     * @return The created room
     */
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = roomService.save(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    /**
     * Update an existing Room
     * @param id The ID of the room to update
     * @param room The updated room data
     * @return The updated room or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        try {
            roomService.getById(id); // Check existence
            room.setId(id);
            roomService.update(room);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a Room
     * @param id The ID of the room to delete
     * @return 200 OK or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        try {
            roomService.getById(id); // Check existence
            roomService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}