package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Showtime;
import com.quak.cinema_reservation_app.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    /**
     * Get Showtime by ID
     * @param id The ID of the showtime
     * @return The showtime or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        try {
            Showtime showtime = showtimeService.getById(id);
            return new ResponseEntity<>(showtime, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get Showtimes by Movie ID
     * @param movieId The ID of the movie
     * @return List of showtimes for that movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieId(@PathVariable Long movieId) {
        List<Showtime> showtimes = showtimeService.getByMovieId(movieId);
        return new ResponseEntity<>(showtimes, HttpStatus.OK);
    }

    /**
     * Create a new Showtime
     * @param showtime The showtime object
     * @return The created showtime
     */
    @PostMapping
    public ResponseEntity<Showtime> createShowtime(@RequestBody Showtime showtime) {
        Showtime savedShowtime = showtimeService.save(showtime);
        return new ResponseEntity<>(savedShowtime, HttpStatus.CREATED);
    }

    /**
     * Update a Showtime
     * @param id The ID of the showtime
     * @param showtime The updated showtime data
     * @return The updated showtime or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long id, @RequestBody Showtime showtime) {
        try {
            showtimeService.getById(id); // Check existence
            showtime.setId(id);
            showtimeService.update(showtime);
            return new ResponseEntity<>(showtime, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a Showtime
     * @param id The ID of the showtime
     * @return 200 OK or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        try {
            showtimeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}