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

    /**
     * Saves a new booking to the database.
     * @param booking The booking object containing user, showtime, and seat IDs.
     * @return The saved Booking object with the generated ID.
     */
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

    /**
     * Finds a booking by its unique ID.
     * @param id The ID of the booking.
     * @return The Booking object.
     */
    public Booking findById(Long id) {
        log.info("Finding booking by id: {}", id);
        String sql = "SELECT * FROM bookings WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, new BookingMapper(), id);
    }

    /**
     * Finds all bookings made by a specific user.
     * @param userId The ID of the user.
     * @return A list of bookings.
     */
    public List<Booking> findByUserId(Long userId) {
        log.info("Finding all bookings for user id: {}", userId);
        String sql = "SELECT * FROM bookings WHERE user_id = ?;";
        return jdbcTemplate.query(sql, new BookingMapper(), userId);
    }

    /**
     * Finds all bookings for a specific showtime.
     * @param showtimeId The ID of the showtime.
     * @return A list of bookings.
     */
    public List<Booking> findByShowtimeId(Long showtimeId) {
        log.info("Finding all bookings for showtime id: {}", showtimeId);
        String sql = "SELECT * FROM bookings WHERE showtime_id = ?;";
        return jdbcTemplate.query(sql, new BookingMapper(), showtimeId);
    }

    /**
     * Retrieves all bookings from the database.
     * @return A list of all Booking objects.
     */
    public List<Booking> findAll() {
        String sql = "SELECT * FROM bookings;";
        log.info("Finding All Bookings");
        return jdbcTemplate.query(sql, new BookingMapper());
    }

    /**
     * Updates an existing booking.
     * @param booking The booking object with updated values.
     */
    public void update(Booking booking){
        log.info("Updating booking id: {}", booking.getId());
        String sql = "UPDATE bookings SET user_id = ?, showtime_id = ?, seat_id = ? WHERE id = ?;";

        jdbcTemplate.update(sql,
                booking.getUserId(),
                booking.getShowtimeId(),
                booking.getSeatId(),
                booking.getId()
        );
    }

    /**
     * Deletes a booking by its ID.
     * @param id The ID of the booking to delete.
     */
    public void deleteById(long id){
        log.info("Deleting Booking id: {}", id);
        findById(id); // Ensure it exists before attempting delete
        String sql = "DELETE FROM bookings WHERE id = ?;";

        jdbcTemplate.update(sql, id);
    }
}