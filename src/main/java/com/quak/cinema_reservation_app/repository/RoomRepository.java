package com.quak.cinema_reservation_app.repository;

import com.quak.cinema_reservation_app.model.Room;
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
public class RoomRepository {

    private static final Logger log = LoggerFactory.getLogger(RoomRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    private static class RoomMapper implements RowMapper<Room> {
        @Override
        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
            Room room = new Room();
            room.setId(rs.getLong("id"));
            room.setName(rs.getString("name"));
            return room;
        }
    }

    public List<Room> findAll() {
        log.info("Finding all rooms");
        String sql = "SELECT * FROM rooms;";
        return jdbcTemplate.query(sql, new RoomMapper());
    }

    public Room findById(Long id) {
        log.info("Finding room by id: {}", id);
        String sql = "SELECT * FROM rooms WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, new RoomMapper(), id);
    }

    public void update(Room room){
        log.info("Updating room");
        String sql = "UPDATE rooms SET name = ? WHERE id = ?;";

        jdbcTemplate.update(sql,
                room.getName(),
                room.getId()
        );
    }

    public Room save(Room room) {
        log.info("Saving new room: {}", room.getName());
        String sql = "INSERT INTO rooms (name) VALUES (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, room.getName());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            room.setId(keyHolder.getKey().longValue());
        }
        return room;
    }

    public void deleteById(long id){
        log.info("Deleting Room");
        findById(id); // Exists
        String sql = "DELETE FROM rooms WHERE id = ?;";

        jdbcTemplate.update(sql, id);
    }
}

