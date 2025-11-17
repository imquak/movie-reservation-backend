package com.quak.cinema_reservation_app.model;

public class Room {

    private Long id;
    private String name;

    public Room() { }

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /** GETTERS AND SETTERS */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "ROOM | " +
                "id=" + id +
                ", name='" + name + '\'';
    }
}