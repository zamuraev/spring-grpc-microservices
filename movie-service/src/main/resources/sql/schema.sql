CREATE TABLE IF NOT EXISTS movies
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(128),
    year INTEGER,
    rating DOUBLE PRECISION,
    genre VARCHAR(128)
);
