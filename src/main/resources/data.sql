-- 1. Insert Movies
INSERT INTO movies (title, description, release_date, duration_minutes, poster_url)
VALUES
('Inception', 'A thief who steals information by entering dreams.', '2010-07-16', 148, 'https://image.tmdb.org/t/p/w500/9gk7admal4zl67YRxIo2s0tombg.jpg'),
('The Matrix', 'A computer hacker learns about the true nature of his reality.', '1999-03-31', 136, 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg');

-- 2. Insert Users
INSERT INTO users (username, email, password, created_at)
VALUES
('john', 'john@email.com', 'pass123', '2025-11-05'),
('rasputin', 'gooble@email.com', 'password', '2024-10-03');

-- 3. Insert Rooms
INSERT INTO rooms (name) VALUES ('Screen 1 (IMAX)');
INSERT INTO rooms (name) VALUES ('Screen 2 (Standard)');

-- 4. Insert Seats for Room 1 (Screen 1)
-- We use a subquery (SELECT id FROM rooms...) to get the correct Room ID automatically.
-- Types: 0=SEAT, 1=WHEELCHAIR, 2=ISLE, 3=EMPTY

-- Row 1: Standard Seats
INSERT INTO seats (room_id, seat_row, seat_col, seat_type) VALUES ((SELECT id FROM rooms WHERE name = 'Screen 1 (IMAX)'), 1, 1, 0);
INSERT INTO seats (room_id, seat_row, seat_col, seat_type) VALUES ((SELECT id FROM rooms WHERE name = 'Screen 1 (IMAX)'), 1, 2, 0);
INSERT INTO seats (room_id, seat_row, seat_col, seat_type) VALUES ((SELECT id FROM rooms WHERE name = 'Screen 1 (IMAX)'), 1, 3, 0);

-- Row 2: Includes a Wheelchair spot (Type 1)
INSERT INTO seats (room_id, seat_row, seat_col, seat_type) VALUES ((SELECT id FROM rooms WHERE name = 'Screen 1 (IMAX)'), 2, 1, 0);
INSERT INTO seats (room_id, seat_row, seat_col, seat_type) VALUES ((SELECT id FROM rooms WHERE name = 'Screen 1 (IMAX)'), 2, 2, 1); -- Wheelchair
INSERT INTO seats (room_id, seat_row, seat_col, seat_type) VALUES ((SELECT id FROM rooms WHERE name = 'Screen 1 (IMAX)'), 2, 3, 0);

-- 5. Insert Seats for Room 2 (Standard)
INSERT INTO seats (room_id, seat_row, seat_col, seat_type) VALUES ((SELECT id FROM rooms WHERE name = 'Screen 2 (Standard)'), 1, 1, 0);
INSERT INTO seats (room_id, seat_row, seat_col, seat_type) VALUES ((SELECT id FROM rooms WHERE name = 'Screen 2 (Standard)'), 1, 2, 0);

-- 6. Insert Showtimes
-- Link 'Inception' to 'Screen 1' tomorrow at 6 PM
INSERT INTO showtimes (start_time, movie_id, room_id)
VALUES (
    '2025-12-25 18:00:00+00',
    (SELECT id FROM movies WHERE title = 'Inception'),
    (SELECT id FROM rooms WHERE name = 'Screen 1 (IMAX)')
);

-- Link 'The Matrix' to 'Screen 2' tomorrow at 8 PM
INSERT INTO showtimes (start_time, movie_id, room_id)
VALUES (
    '2025-12-25 20:00:00+00',
    (SELECT id FROM movies WHERE title = 'The Matrix'),
    (SELECT id FROM rooms WHERE name = 'Screen 2 (Standard)')
);