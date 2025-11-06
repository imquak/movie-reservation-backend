package com.quak.cinema_reservation_app.repository;

import com.quak.cinema_reservation_app.model.Movie;
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
import java.sql.Statement;
import java.util.List;

@Repository // Tells Spring this class is for data access
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    // Inject JDBCTemplate
    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    // Build a 'Movie' object from a database row.
    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));

            if (rs.getDate("created_at") != null) {
                user.setCreatedAt(rs.getDate("created_at").toLocalDate());
            }
            return user;
        }
    }

    /**
     * Gets all users from the database.
     * @return a List of User objects
     */
    public List<User> findAll() {
        String sql = "SELECT * FROM users;";

        // We use the jdbcTemplate to run the query.
        // We pass it the SQL and our RowMapper.
        // It handles the connection, runs the query, and uses our
        // mapper to build a list of User objects.
        return jdbcTemplate.query(sql, new UserMapper());
    }

    /**
     * Finds a single user by its ID.
     * @param id The ID of the user to find.
     * @return The User object.
     * @throws org.springframework.dao.EmptyResultDataAccessException if no user is found.
     */
    public User findById(Long id) {
        // Here is the SQL query you asked about
        String sql = "SELECT * FROM users WHERE id = ?;";

        // We use queryForObject for one item
        // We pass the sql, the mapper, and the id to fill the '?'
        return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
    }

    /**
     * Saves a new user to the database and returns the user with its new ID.
     * @param user The User object to save (without an ID).
     * @return The same User object, now updated with the auto-generated ID.
     */
    public User save(User user) {

        String sql = "INSERT INTO users (username,email,password,created_at) VALUES (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});

            ps.setString(1,user.getUsername());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPassword());
            ps.setObject(4,user.getCreatedAt());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            user.setId(keyHolder.getKey().longValue());
        }

        return user;
    }

    public void update(User user){
        log.info("Updating user");
        String sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?;";

        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getId()
        );
    }

    public void deleteById(long id){
        log.info("Deleting User");
        findById(id); // Exists
        String sql = "DELETE FROM users WHERE id = ?;";

        jdbcTemplate.update(sql, id);
    }
}
