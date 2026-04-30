CREATE DATABASE library_db;

CREATE TABLE books(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(30) UNIQUE,
    publish_year INTEGER,
    genre VARCHAR(50)
);
--Add a record to the database--
INSERT INTO books(title, author, isbn, publish_year, genre)
VALUES('Betrayal In Kutus', 'Everblue Space', '978-0123456789', 2026, 'Drama');
INSERT INTO books(title, author, isbn, publish_year, genre)
VALUES('Chasing The Stars', 'Everblue Space', '987-0123456790', 2023, 'Sci-Fi');