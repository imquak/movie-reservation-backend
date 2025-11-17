-- Drop tables in reverse order of dependency
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS showtime;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS movies;

-- Create core tables
CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at DATE
);

CREATE TABLE movies (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    release_date DATE,
    duration_minutes INT,
    poster_url VARCHAR(500)
);

CREATE TABLE rooms (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create 'map' table for seats
CREATE TABLE seats (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id BIGINT NOT NULL,
    seat_row INT NOT NULL,
    seat_col INT NOT NULL,
    seat_type INT NOT NULL,

    CONSTRAINT fk_room FOREIGN KEY(room_id) REFERENCES rooms(id)
);

-- Create relationship table for showtime
CREATE TABLE showtime (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    movie_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,

    CONSTRAINT fk_movie FOREIGN KEY(movie_id) REFERENCES movies(id),
    CONSTRAINT fk_room FOREIGN KEY(room_id) REFERENCES rooms(id)
);

-- Create final relationship table for bookings
CREATE TABLE bookings (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    booking_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    user_id BIGINT NOT NULL,
    showtime_id BIGINT NOT NULL,
    seat_id BIGINT NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_showtime FOREIGN KEY(showtime_id) REFERENCES showtime(id),
    CONSTRAINT fk_seat FOREIGN KEY(seat_id) REFERENCES seats(id),

    CONSTRAINT unique_seat_per_showtime UNIQUE(showtime_id, seat_id)
);