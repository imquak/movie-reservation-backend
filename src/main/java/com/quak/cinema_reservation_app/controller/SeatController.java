package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Seat;
import com.quak.cinema_reservation_app.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    /**
     * Get all Seats
     * @return List of all seats
     */
    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAll();
    }

    /**
     * Get Seat by ID
     * @param id The ID of the seat
     * @return The seat or 404 Not Found
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Seat> getById(@PathVariable Long id) {
        try {
            Seat seat = seatService.getById(id);
            return new ResponseEntity<>(seat, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all Seats for a specific Room
     * @param room_id The ID of the room
     * @return List of seats in the room or 404 Not Found
     */
    @GetMapping("/room/{room_id}")
    public ResponseEntity<List<Seat>> getAllByRoomId(@PathVariable Long room_id) {
        try {
            List<Seat> seats = seatService.getAllByRoom(room_id);
            return new ResponseEntity<>(seats, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new Seat
     * @param seat The seat details (room_id, seat_row, seat_col, seat_type)
     * @return The created seat
     */
    @PostMapping
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat) {
        Seat savedSeat = seatService.save(seat);
        return new ResponseEntity<>(savedSeat, HttpStatus.CREATED);
    }

    /**
     * Update an existing Seat
     * @param id The ID of the seat to update
     * @param seat The updated seat details (room_id, seat_row, seat_col, seat_type)
     * @return The updated seat or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id, @RequestBody Seat seat) {
        try {
            seatService.getById(id);
            seat.setId(id);
            seatService.update(seat);
            return new ResponseEntity<>(seat, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a Seat by ID
     * @param id The ID of the seat to delete
     * @return 200 OK or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        try {
            seatService.getById(id);
            seatService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}