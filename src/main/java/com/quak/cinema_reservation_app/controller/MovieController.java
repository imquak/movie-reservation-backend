package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Movie;
import com.quak.cinema_reservation_app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

// This combines @Controller and @ResponseBody.
// It tells Spring to handle web requests and return JSON.
@RestController
// Sets the base URL for all methods in this class
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    // We inject our MovieRepository, just like we injected JdbcTemplate
    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /** Get all Movies */
    @GetMapping
    public List<Movie> getAllMovies() {
        // We use our repository to get the data
        return movieRepository.findAll();
    }

    /** Get Movie by {id} */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        try {
            Movie movie = movieRepository.findById(id);
            // If found, return the movie with a 200 OK status
            return new ResponseEntity<>(movie, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            // If findById throws this error (meaning 0 rows found),
            // we return a 404 Not Found status.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Create Movie via ResponseEntity */
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        // @RequestBody converts the JSON body into a Movie object
        Movie savedMovie = movieRepository.save(movie);

        // Return the newly saved movie (with its ID) and a 201 Created status
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        try {
            // Check if the movie exists by trying to find it
            movieRepository.findById(id);

            // If it exists, set the ID on the movie object and update it
            movie.setId(id);
            movieRepository.update(movie);

            // Return the updated movie and a 200 OK status
            return new ResponseEntity<>(movie, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            // If it doesn't exist, return a 404 Not Found status
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        try {
            movieRepository.findById(id);

            // If it exists, delete it
            movieRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}