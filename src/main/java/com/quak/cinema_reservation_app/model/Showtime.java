package com.quak.cinema_reservation_app.model;

import java.time.OffsetDateTime;

public class Showtime {

    private Long id;
    private OffsetDateTime start_time; // Timestamp in SQL
    private Long movie_id;
    private Long room_id;

    public Showtime() { }

    public Showtime(Long id, OffsetDateTime startTime, Long movieId, Long roomId) {
        this.id = id;
        this.start_time = startTime;
        this.movie_id = movieId;
        this.room_id = roomId;
    }

    /** GETTERS AND SETTERS */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OffsetDateTime getStartTime() { return start_time; }
    public void setStartTime(OffsetDateTime startTime) { this.start_time = startTime; }

    public Long getMovieId() { return movie_id; }
    public void setMovieId(Long movieId) { this.movie_id = movieId; }

    public Long getRoomId() { return room_id; }
    public void setRoomId(Long roomId) { this.room_id = roomId; }

    @Override
    public String toString() {
        return "USER | " +
                "id=" + id +
                ", start_time='" + start_time + '\'' +
                ", movie_id='" + movie_id + '\'' +
                ", room_id='" + room_id + '\'';
    }
}