package com.quak.cinema_reservation_app.service;

import com.quak.cinema_reservation_app.model.Booking;
import com.quak.cinema_reservation_app.model.Seat;
import com.quak.cinema_reservation_app.model.Seat.SeatType;
import com.quak.cinema_reservation_app.model.Showtime;
import com.quak.cinema_reservation_app.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
                          ShowtimeRepository showtimeRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    /**
     * Creates a new booking after validating seat availability and type.
     * @param userId The ID of the user making the booking.
     * @param showtimeId The ID of the showtime.
     * @param seatId The ID of the seat.
     * @return The created Booking object.
     * @throws IllegalArgumentException if the seat is invalid or already taken.
     */
    public Booking createBooking(Long userId, Long showtimeId, Long seatId) {
        log.info("Creating booking for user {} on showtime {} for seat {}", userId, showtimeId, seatId);

        // Verify entities exist
        userRepository.findById(userId);
        Showtime showtime = showtimeRepository.findById(showtimeId);
        Seat seat = seatRepository.findById(seatId);

        // Validate seat belongs to the room of the showtime
        if (!seat.getRoomId().equals(showtime.getRoomId())) {
            throw new IllegalArgumentException("This seat is not in the room for this showtime.");
        }

        // Validate seat type
        if (seat.getSeatType() != SeatType.SEAT && seat.getSeatType() != SeatType.WHEELCHAIR) {
            throw new IllegalArgumentException("This spot is an " + seat.getSeatType() + " and cannot be booked.");
        }

        Booking newBooking = new Booking();
        newBooking.setUserId(userId);
        newBooking.setShowtimeId(showtimeId);
        newBooking.setSeatId(seatId);

        try {
            return bookingRepository.save(newBooking);
        } catch (DataIntegrityViolationException e) {
            log.warn("Booking failed: Seat {} is already booked for showtime {}", seatId, showtimeId);
            throw new IllegalArgumentException("This seat is already taken for this showtime.");
        }
    }

    /**
     * Retrieves all bookings.
     * @return List of all bookings.
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Retrieves a specific booking by ID.
     * @param id The ID of the booking.
     * @return The Booking object.
     */
    public Booking getById(long id) {
        return bookingRepository.findById(id);
    }

    /**
     * Deletes a booking by ID.
     * @param id The ID of the booking to delete.
     */
    public void deleteById(long id) {
        bookingRepository.deleteById(id);
    }
}