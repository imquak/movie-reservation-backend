package com.quak.cinema_reservation_app.controller;

import com.quak.cinema_reservation_app.model.Booking;
import com.quak.cinema_reservation_app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) { this.bookingService = bookingService; }

    /** * Get all Bookings
     * @return List of all bookings
     */
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    /** * Get Booking by {id}
     * @param id The ID of the booking
     * @return The booking or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        try {
            Booking booking = bookingService.getById(id);
            return new ResponseEntity<>(booking, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** * Create Booking via ResponseEntity
     * @param booking The booking details (user_id, showtime_id, seat_id)
     * @return The created booking
     */
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        try {
            // We delegate to the service method that handles validation logic
            Booking savedBooking = bookingService.createBooking(
                    booking.getUserId(),
                    booking.getShowtimeId(),
                    booking.getSeatId()
            );
            return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Returns 400 Bad Request if seat is taken or invalid
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** * Delete Booking by {id}
     * @param id The ID of the booking to delete
     * @return 200 OK or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.getById(id); // Check existence
            bookingService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}