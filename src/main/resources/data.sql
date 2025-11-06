-- This file will be run after schema.sql

-- Insert sample movies
INSERT INTO movies (title, description, release_date, duration_minutes, poster_url)
VALUES
('Inception',
 'A thief who steals information by entering people''s dreams.',
 '2010-07-16',
 148,
 'https://example.com/posters/inception.jpg');

INSERT INTO movies (title, description, release_date, duration_minutes, poster_url)
VALUES
('The Matrix',
 'A computer hacker learns about the true nature of his reality.',
 '1999-03-31',
 136,
 'https://example.com/posters/matrix.jpg');

-- Insert a sample user
INSERT INTO users (username, email, password, created_at)
VALUES
('john',
 'john@email.com',
 'pass123',
 '2025-11-05');

INSERT INTO users (username, email, password, created_at)
VALUES
('rasputin',
  'gooble@email.com',
  'password',
  '2024-10-03');