package com.quak.cinema_reservation_app.repository;

import com.quak.cinema_reservation_app.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository // Tells Spring this class is for data access
public class MovieRepository {
    private static final Logger log = LoggerFactory.getLogger(MovieRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    private static class MovieMapper implements RowMapper<Movie> {
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Movie movie = new Movie();
            log.info("Creating Movie");

            movie.setId(rs.getLong("id"));
            movie.setTitle(rs.getString("title"));
            movie.setDescription(rs.getString("description"));

            if (rs.getDate("release_date") != null) {
                movie.setReleaseDate(rs.getDate("release_date").toLocalDate());
            }

            movie.setDurationMinutes(rs.getInt("duration_minutes"));
            movie.setPosterUrl(rs.getString("poster_url"));

            return movie;
        }
    }

    /**
     * Gets all movies from the database.
     * @return a List of Movie objects
     */
    public List<Movie> findAll() {
        String sql = "SELECT * FROM movies;";
        log.info("Finding All Movies");

        return jdbcTemplate.query(sql, new MovieMapper());
    }

    /**
     * Finds a single movie by its ID.
     * @param id The ID of the movie to find.
     * @return The Movie object.
     * @throws org.springframework.dao.EmptyResultDataAccessException if no movie is found.
     */
    public Movie findById(Long id) {
        log.info("Finding Movie by Id");
        String sql = "SELECT * FROM movies WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, new MovieMapper(), id);
    }

    /**
     * Saves a new movie to the database and returns the movie with its new ID.
     * @param movie The Movie object to save (without an ID).
     * @return The same Movie object, now updated with the auto-generated ID.
     */
    public Movie save(Movie movie) {
        log.info("Saving Movie");
        String sql = "INSERT INTO movies (title, description, release_date, duration_minutes, poster_url) " +
                "VALUES (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});

            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDescription());
            ps.setObject(3, movie.getReleaseDate()); // Use setObject for LocalDate
            ps.setInt(4, movie.getDurationMinutes());
            ps.setString(5, movie.getPosterUrl());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            movie.setId(keyHolder.getKey().longValue());
        }

        return movie;
    }

    public void update(Movie movie){
        log.info("Updating Movie");
        String sql = "UPDATE movies SET title = ?, description = ?, release_date = ?, " +
                "duration_minutes = ?, poster_url = ? WHERE id = ?;";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDescription());
            ps.setObject(3, movie.getReleaseDate()); // Use setObject for LocalDate
            ps.setInt(4, movie.getDurationMinutes());
            ps.setString(5, movie.getPosterUrl());
            ps.setLong(6, movie.getId());
            return ps;
        });
    }

    public void deleteById(long id){
        log.info("Deleting Movie");
        findById(id); // Exists
        String sql = "DELETE FROM movies WHERE id = ?;";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,id);
            return ps;
        });
    }
}