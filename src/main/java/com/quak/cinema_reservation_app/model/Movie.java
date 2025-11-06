package com.quak.cinema_reservation_app.model;

import java.time.LocalDate;

public class Movie {

    private Long id;
    private String title;
    private String description;
    private LocalDate release_date; // LocalDate is DATE type in SQL
    private Integer duration_minutes;
    private String poster_url;

    public Movie() { }

    public Movie(Long id, String title, String description, LocalDate releaseDate, Integer durationMinutes, String posterUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.release_date = releaseDate;
        this.duration_minutes = durationMinutes;
        this.poster_url = posterUrl;
    }

    /** GETTER AND SETTER */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getReleaseDate() { return release_date; }
    public void setReleaseDate(LocalDate releaseDate) { this.release_date = releaseDate; }

    public Integer getDurationMinutes() { return duration_minutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.duration_minutes = durationMinutes; }

    public String getPosterUrl() { return poster_url; }
    public void setPosterUrl(String posterUrl) { this.poster_url = posterUrl; }

    @Override
    public String toString() {
        return "MOVIE | " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", release_date=" + release_date +
                ", duration_minutes='" + duration_minutes + '\'' +
                ", poster_url='" + poster_url;
    }
}