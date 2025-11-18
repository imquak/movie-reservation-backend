package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Movie;
import com.quak.cinema_reservation_app.model.User;
import com.quak.cinema_reservation_app.repository.UserRepository;
import com.quak.cinema_reservation_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;

// This combines @Controller and @ResponseBody.
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles HTTP GET requests to "/api/users"
     * @return A list of all movies as JSON
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Handles HTTP GET requests to "/api/users/{id}"
     * e.g., /api/usewrs/1 or /api/users/2
     * @param id The ID from the URL
     * @return A single movie, or a 404 Not Found error
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /** Create User via ResponseEntity */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDate.now());
        }

        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            userService.findById(id);
            user.setId(id);
            userService.update(user);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        try {
            userRepository.findById(id);
            userRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}