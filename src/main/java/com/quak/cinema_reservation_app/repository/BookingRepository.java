package com.quak.cinema_reservation_app.repository;

import com.quak.cinema_reservation_app.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class BookingRepository {

    private static final Logger log = LoggerFactory.getLogger(BookingRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookingRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    private static class BookingMapper implements RowMapper<Booking> {
        @Override
        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
            Booking booking = new Booking();
            booking.setId(rs.getLong("id"));
            booking.setUserId(rs.getLong("user_id"));
            booking.setShowtimeId(rs.getLong("showtime_id"));
            booking.setSeatId(rs.getLong("seat_id"));
            booking.setBookingTime(rs.getObject("booking_time", OffsetDateTime.class));
            return booking;
        }
    }

    public Booking save(Booking booking) {
        // We don't insert booking_time, it has a DEFAULT in the DB
        String sql = "INSERT INTO bookings (user_id, showtime_id, seat_id) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, booking.getUserId());
            ps.setLong(2, booking.getShowtimeId());
            ps.setLong(3, booking.getSeatId());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            booking.setId(keyHolder.getKey().longValue());
        }
        return booking;
    }

    public List<Booking> findByUserId(Long userId) {
        log.info("Finding all bookings for user id: {}", userId);
        String sql = "SELECT * FROM bookings WHERE user_id = ?;";
        return jdbcTemplate.query(sql, new BookingMapper(), userId);
    }

    public List<Booking> findByShowtimeId(Long showtimeId) {
        log.info("Finding all bookings for showtime id: {}", showtimeId);
        String sql = "SELECT * FROM bookings WHERE showtime_id = ?;";
        return jdbcTemplate.query(sql, new BookingMapper(), showtimeId);
    }
}