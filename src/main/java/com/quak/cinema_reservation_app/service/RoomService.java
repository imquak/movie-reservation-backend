package com.quak.cinema_reservation_app.service;

import com.quak.cinema_reservation_app.model.Room;
import com.quak.cinema_reservation_app.model.Seat;
import com.quak.cinema_reservation_app.model.User;
import com.quak.cinema_reservation_app.repository.RoomRepository;
import com.quak.cinema_reservation_app.repository.SeatRepository;
import com.quak.cinema_reservation_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository){ this.roomRepository = roomRepository; }

    public List<Room> getAll() { return roomRepository.findAll(); }
    public Room getById(long id) { return roomRepository.findById(id); }
    public Room save(Room room){ return roomRepository.save(room); }
    public void deleteById(long id){ roomRepository.deleteById(id); }
    public void update(Room room){ roomRepository.update(room); }
}
