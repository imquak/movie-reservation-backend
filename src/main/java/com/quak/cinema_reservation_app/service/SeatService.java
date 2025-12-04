package com.quak.cinema_reservation_app.service;

import com.quak.cinema_reservation_app.model.Seat;
import com.quak.cinema_reservation_app.model.User;
import com.quak.cinema_reservation_app.repository.SeatRepository;
import com.quak.cinema_reservation_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SeatService {
    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository){ this.seatRepository = seatRepository; }

    public List<Seat> getAll() { return seatRepository.findAll(); }
    public List<Seat> getAllByRoom(long room_id){ return seatRepository.findAllFromRoom(room_id); }
    public Seat getById(long id) { return seatRepository.findById(id); }
    public Seat save(Seat seat){ return seatRepository.save(seat); }
    public void deleteById(long id){ seatRepository.deleteById(id); }
    public void update(Seat seat){ seatRepository.update(seat); }
}
