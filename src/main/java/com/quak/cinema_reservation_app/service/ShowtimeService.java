package com.quak.cinema_reservation_app.service;

import com.quak.cinema_reservation_app.model.Seat;
import com.quak.cinema_reservation_app.model.Showtime;
import com.quak.cinema_reservation_app.model.User;
import com.quak.cinema_reservation_app.repository.SeatRepository;
import com.quak.cinema_reservation_app.repository.ShowtimeRepository;
import com.quak.cinema_reservation_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository){ this.showtimeRepository = showtimeRepository; }

    public Showtime getById(long id) { return showtimeRepository.findById(id); }
    public List<Showtime> getByMovieId(long movie_id) { return showtimeRepository.findByMovieId(movie_id); }
    public Showtime save(Showtime showtime){ return showtimeRepository.save(showtime); }
    public void deleteById(long id){ showtimeRepository.deleteById(id); }
    public void update(Showtime showtime){ showtimeRepository.update(showtime); }
}
