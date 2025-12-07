package com.quak.cinema_reservation_app.service;
import com.quak.cinema_reservation_app.model.*;
import com.quak.cinema_reservation_app.model.Seat.SeatType;
import com.quak.cinema_reservation_app.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

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

    public Booking createBooking(Long userId, Long showtimeId, Long seatId) {
        log.info("Creating booking for user {} on showtime {} for seat {}", userId, showtimeId, seatId);

        userRepository.findById(userId);
        Showtime showtime = showtimeRepository.findById(showtimeId);
        Seat seat = seatRepository.findById(seatId);

        if (!seat.getRoomId().equals(showtime.getRoomId())) {
            throw new IllegalArgumentException("This seat is not in the room for this showtime.");
        }

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


}