## Library Management System (Java CRUD)

This project is a desktop-based Library Management System built with Java Swing and PostgreSQL. It utilizes the DAO (Data Access Object) Design Pattern to separate database logic from the user interface.
Project Components

## The system consists of the following four core files:

1.Book.java: The model class that defines the book entity with attributes such as title, author, ISBN, publish year, and genre.  

2.DatabaseConnection.java: A utility class that manages the JDBC connection to the PostgreSQL database.  

3.BookDAO.java: The Data Access Object that contains SQL logic for adding, finding, updating, and deleting records.  

4.BookGui.java: The graphical user interface built with Java Swing that allows users to interact with the data through a table and input form.  

## Features

1.Create: Users can input book details and save them to the database.  

2.Read: The application retrieves all records and displays them in a JTable.  

3.Update: Users can select a record from the table and modify its details.  

4.Delete: Selected records can be removed after a confirmation prompt.  

5.Search: A filter feature allows users to search for books by title.  

6.Large UI: The interface is configured with a size of 1200x900 and a font size of 24 for improved visibility.  

## Setup Instructions
1. Database Table

Run the following SQL command in your PostgreSQL terminal to create the necessary table:
```
CREATE TABLE books (
id SERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
author VARCHAR(255),
isbn VARCHAR(50) UNIQUE NOT NULL,
publish_year INTEGER,
genre VARCHAR(100)
);
```
2. Connection Settings
In DatabaseConnection.java, update the URL, USER, and PASSWORD variables to match your local PostgreSQL configuration.

3. Dependencies
Ensure the PostgreSQL JDBC driver is added to your project's classpath. If using Maven, add the postgresql dependency to your pom.xml.

4. Running the App

Run the BookGui.java file to launch the application interface.
