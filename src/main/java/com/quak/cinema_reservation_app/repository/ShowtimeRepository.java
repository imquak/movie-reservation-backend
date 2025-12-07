package com.quak.cinema_reservation_app.repository;

import com.quak.cinema_reservation_app.model.Seat;
import com.quak.cinema_reservation_app.model.Showtime;
import com.quak.cinema_reservation_app.model.User;
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
import java.time.OffsetDateTime; // We need this for TIMESTAMP WITH TIME ZONE
import java.util.List;

@Repository
public class ShowtimeRepository {

    private static final Logger log = LoggerFactory.getLogger(ShowtimeRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShowtimeRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    private static class ShowtimeMapper implements RowMapper<Showtime> {
        @Override
        public Showtime mapRow(ResultSet rs, int rowNum) throws SQLException {
            Showtime showtime = new Showtime();
            showtime.setId(rs.getLong("id"));
            showtime.setMovieId(rs.getLong("movie_id"));
            showtime.setRoomId(rs.getLong("room_id"));
            showtime.setStartTime(rs.getObject("start_time", OffsetDateTime.class));

            return showtime;
        }
    }

    public Showtime save(Showtime showtime) {
        String sql = "INSERT INTO showtimes (movie_id, room_id, start_time) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, showtime.getMovieId());
            ps.setLong(2, showtime.getRoomId());
            ps.setObject(3, showtime.getStartTime()); // Use setObject
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            showtime.setId(keyHolder.getKey().longValue());
        }
        return showtime;
    }

    public Showtime findById(Long id) {
        String sql = "SELECT * FROM showtimes WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, new ShowtimeMapper(), id);
    }

    public List<Showtime> findByMovieId(Long movieId) {
        log.info("Finding all showtimes for movie id: {}", movieId);
        String sql = "SELECT * FROM showtimes WHERE movie_id = ?;";
        return jdbcTemplate.query(sql, new ShowtimeMapper(), movieId);
    }

    public void update(Showtime showtime){
        log.info("Updating showtime");
        String sql = "UPDATE showtimes SET start_time = ?, movie_id = ?, room_id = ? WHERE id = ?;";

        jdbcTemplate.update(sql,
                showtime.getStartTime(),
                showtime.getMovieId(),
                showtime.getRoomId(),
                showtime.getId()
        );
    }

    public void deleteById(long id){
        log.info("Deleting showtime");
        findById(id); // Exists
        String sql = "DELETE FROM showtimes WHERE id = ?;";

        jdbcTemplate.update(sql, id);
    }
}