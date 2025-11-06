package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Movie;
import com.quak.cinema_reservation_app.model.User;
import com.quak.cinema_reservation_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;

// This combines @Controller and @ResponseBody.
// It tells Spring to handle web requests and return JSON.
@RestController
// Sets the base URL for all methods in this class
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    // We inject our MovieRepository, just like we injected JdbcTemplate
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles HTTP GET requests to "/api/users"
     * @return A list of all movies as JSON
     */
    @GetMapping
    public List<User> getAllUsers() {
        // We use our repository to get the data
        return userRepository.findAll();
    }

    /**
     * Handles HTTP GET requests to "/api/users/{id}"
     * e.g., /api/usewrs/1 or /api/users/2
     * @param id The ID from the URL
     * @return A single movie, or a 404 Not Found error
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        //We return responseEntity since if we don't find we have to catch the exception
        try {
            User user = userRepository.findById(id);
            // If found, return the user with a 200 OK status
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            // If findById throws this error (meaning 0 rows found),
            // we return a 404 Not Found status.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /** Create User via ResponseEntity */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // @RequestBody converts the JSON body into a Movie object

        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDate.now());
        }

        User savedUser = userRepository.save(user);

        // Return the newly saved movie (with its ID) and a 201 Created status
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            // Check if the user exists by trying to find it
            userRepository.findById(id);

            // If it exists, set the ID on the user object and update it
            user.setId(id);
            userRepository.update(user);

            // Return the updated movie and a 200 OK status
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            // If it doesn't exist, return a 404 Not Found status
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        try {
            userRepository.findById(id);

            // If it exists, delete it
            userRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}