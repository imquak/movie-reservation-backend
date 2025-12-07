package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Booking;
import com.quak.cinema_reservation_app.model.Movie;
import com.quak.cinema_reservation_app.repository.MovieRepository;
import com.quak.cinema_reservation_app.service.BookingService;
import com.quak.cinema_reservation_app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

// This combines @Controller and @ResponseBody.
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) { this.bookingService = bookingService; }

    /** Get all Bookings */
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.createBooking();
    }

    /** Get Movie by {id} */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        try {
            Movie movie = movieService.getById(id);
            return new ResponseEntity<>(movie, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Create Movie via ResponseEntity */
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.save(movie);

        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        try {
            movieService.getById(id);
            movie.setId(id);
            movieService.update(movie);

            return new ResponseEntity<>(movie, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        try {
            movieService.getById(id);
            movieService.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}