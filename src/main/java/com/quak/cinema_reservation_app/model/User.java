package com.quak.cinema_reservation_app.model;

import java.time.LocalDate;

public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDate created_at; // LocalDate is Date in SQL

    public User() { }

    public User(Long id, String username, String email, String password, LocalDate createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.created_at = createdAt;
    }

    /** GETTER AND SETTER */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getCreatedAt() { return created_at; }
    public void setCreatedAt(LocalDate createdAt) { this.created_at = createdAt; }

    @Override
    public String toString() {
        return "USER | " +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", created_at=" + created_at;
    }
}