package com.quak.cinema_reservation_app.model;

import java.time.OffsetDateTime;

public class Booking {

    private Long id;
    private OffsetDateTime booking_time;

    private Long user_id;
    private Long showtime_id;
    private Long seat_id;

    public Booking() { }

    public Booking(Long id, OffsetDateTime bookingTime, Long userId, Long showtimeId, Long seatId) {
        this.id = id;
        this.booking_time = bookingTime;
        this.user_id = userId;
        this.showtime_id = showtimeId;
        this.seat_id = seatId;
    }

    /** GETTERS AND SETTERS */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OffsetDateTime getBookingTime() { return booking_time; }
    public void setBookingTime(OffsetDateTime bookingTime) { this.booking_time = bookingTime; }

    public Long getUserId() { return user_id; }
    public void setUserId(Long userId) { this.user_id = userId; }

    public Long getShowtimeId() { return showtime_id; }
    public void setShowtimeId(Long showtimeId) { this.showtime_id = showtimeId; }

    public Long getSeatId() { return seat_id; }
    public void setSeatId(Long seatId) { this.seat_id = seatId; }

    @Override
    public String toString() {
        return "BOOKING | " +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", showtime_id=" + showtime_id +
                ", seat_id='" + seat_id + '\'' +
                ", time='" + booking_time + '\'';
    }
}