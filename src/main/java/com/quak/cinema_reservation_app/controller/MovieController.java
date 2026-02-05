package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Movie;
import com.quak.cinema_reservation_app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Get all Movies
     * @return List of all movies
     */
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    /**
     * Get Movie by ID
     * @param id The ID of the movie
     * @return The movie or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        try {
            Movie movie = movieService.getById(id);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new Movie
     * @param movie The movie details (title, description, release_date, duration_minutes, poster_url)
     * @return The created movie
     */
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.save(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    /**
     * Update an existing Movie
     * @param id The ID of the movie to update
     * @param movie The updated movie details (title, description, release_date, duration_minutes, poster_url)
     * @return The updated movie or 404 Not Found
     */
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

    /**
     * Delete a Movie by ID
     * @param id The ID of the movie to delete
     * @return 200 OK or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        try {
            movieService.getById(id);
            movieService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}