package com.quak.cinema_reservation_app.service;

import com.quak.cinema_reservation_app.model.Movie;
import com.quak.cinema_reservation_app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){ this.movieRepository = movieRepository; }

    public List<Movie> getAllMovies() { return movieRepository.findAll(); }
    public Movie getById(long id){ return movieRepository.findById(id); }
    public Movie save(Movie movie){ return movieRepository.save(movie); }
    public void deleteById(long id){ movieRepository.deleteById(id); }
    public void update(Movie movie){ movieRepository.update(movie); }



}
