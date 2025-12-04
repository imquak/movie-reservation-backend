package com.quak.cinema_reservation_app.repository;

import com.quak.cinema_reservation_app.model.Seat;
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
import java.util.List;

@Repository
public class SeatRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(SeatRepository.class);

    @Autowired
    public SeatRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    private static class SeatMapper implements RowMapper<Seat> {
        @Override
        public Seat mapRow(ResultSet rs, int rowNum) throws SQLException {
            Seat seat = new Seat();
            seat.setId(rs.getLong("id"));
            seat.setRoomId(rs.getLong("room_id"));
            seat.setSeatRow(rs.getInt("seat_row"));
            seat.setSeatCol(rs.getInt("seat_col"));
            seat.setSeatType(Seat.getSeatTypeFromIndex(rs.getInt("seat_type")));

            return seat;
        }
    }

    /**
     * Gets all seats from the database.
     * @return a List of Seat objects
     */
    public List<Seat> findAll() {
        String sql = "SELECT * FROM seats;";
        return jdbcTemplate.query(sql, new SeatMapper());
    }

    public List<Seat> findAllFromRoom(Long id) {
        log.info("Finding seats in room with id: {}", id);
        String sql = "SELECT * FROM seats WHERE room_id = ?;";
        return jdbcTemplate.query(sql, new SeatMapper(), id);
    }

    /**
     * Finds a single seat by its ID.
     * @param id The ID of the seat to find.
     * @return The Seat object.
     * @throws org.springframework.dao.EmptyResultDataAccessException if no user is found.
     */
    public Seat findById(Long id) {
        String sql = "SELECT * FROM seats WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new SeatMapper(), id);
    }

    /**
     * Saves a new user to the database and returns the user with its new ID.
     * @param seat The Seat object to save (without an ID).
     * @return The same Seat object, now updated with the auto-generated ID.
     */
    public Seat save(Seat seat) {

        String sql = "INSERT INTO seat (room_id,seat_row,seat_col,seat_type) VALUES (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});

            ps.setLong(1,seat.getRoomId());
            ps.setInt(2,seat.getSeatRow());
            ps.setInt(3,seat.getSeatCol());
            ps.setInt(4,seat.getSeatType().getValue());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            seat.setId(keyHolder.getKey().longValue());
        }

        return seat;
    }

    public void update(Seat seat){
        log.info("Updating seat");
        String sql = "UPDATE seats SET room_id = ?, seat_row = ?, seat_col = ? seat_type = ? WHERE id = ?;";

        jdbcTemplate.update(sql,
                seat.getRoomId(),
                seat.getSeatRow(),
                seat.getSeatCol(),
                seat.getSeatType().getValue()
        );
    }

    public void deleteById(long id){
        log.info("Deleting Seat");
        findById(id); // Exists
        String sql = "DELETE FROM seats WHERE id = ?;";

        jdbcTemplate.update(sql, id);
    }
}
