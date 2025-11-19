package com.quak.cinema_reservation_app.service;

import com.quak.cinema_reservation_app.model.User;
import com.quak.cinema_reservation_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){ this.userRepository = userRepository; }

    public List<User> getAllMovies() { return userRepository.findAll(); }
    public User getById(long id){ return userRepository.findById(id); }
    public User save(User user){ return userRepository.save(user); }
    public void deleteById(long id){ userRepository.deleteById(id); }
    public void update(User user){ userRepository.update(user); }



}
