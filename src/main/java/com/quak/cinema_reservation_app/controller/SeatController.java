package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Movie;
import com.quak.cinema_reservation_app.model.Seat;
import com.quak.cinema_reservation_app.repository.MovieRepository;
import com.quak.cinema_reservation_app.service.MovieService;
import com.quak.cinema_reservation_app.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

// This combines @Controller and @ResponseBody.
@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    /** Get all Seats */
    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAll();
    }

    /** Get Seat by {id} */
    @GetMapping("/id/{id}")
    public ResponseEntity<Seat> getById(@PathVariable Long id) {
        try {
            Seat seat = seatService.getById(id);
            return new ResponseEntity<>(seat, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Get Seat by {room_id} */
    @GetMapping("/room/{room_id}")
    public ResponseEntity<List<Seat>> getAllByRoomId(@PathVariable Long room_id) {
        try {
            List<Seat> seats = seatService.getAllByRoom(room_id);
            return new ResponseEntity<>(seats, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Create Seat via ResponseEntity */
    @PostMapping
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat) {
        Seat savedSeat = seatService.save(seat);

        return new ResponseEntity<>(savedSeat, HttpStatus.CREATED);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Seat> deleteSeat(@PathVariable Long id) {
        try {
            seatService.getById(id);
            seatService.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}