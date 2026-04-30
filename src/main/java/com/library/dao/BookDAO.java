/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.dao;

/**
 *
 * @author Emperor_RR
 */
import com.library.model.Book;
import com.library.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet; // The "Table" we get back from Postgres
import java.sql.Statement;

public class BookDAO {
    public void addBook(Book book){
        String sql = "INSERT INTO books(title, author, isbn, publish_year, genre) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            //map placeholders('?') with real values of the book object 
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setInt(4, book.getPublishYear());
            pstmt.setString(5, book.getGenre());
            
            //send data to the database...postgre
            pstmt.executeUpdate();
            System.out.println("Book saved successfully in the database!");
        } catch(SQLException e){
            System.out.println("Failed to save the book: " + e.getMessage());
        }
    }
    
    public List<Book> findAll(){
        //create a list to store books returned from the database
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books;";
        
        try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        // move through the result set row by row
        while (rs.next()) {
            //Extract data from the current row
            String title = rs.getString("title");
            String author = rs.getString("author");
            String isbn = rs.getString("isbn");
            int year = rs.getInt("publish_year");
            String genre = rs.getString("genre");

            // create a Book object from that data
            Book book = new Book(title, author, isbn, year, genre);

            //add to the list
            books.add(book);
        }
    } catch (SQLException e) {
        System.out.println("Error reading books: " + e.getMessage());
    }

    return books; // Return all books
    }
    
    public void updateBook(Book book) {
    String sql = "UPDATE books SET title = ?, author = ?, publish_year = ?, genre = ? WHERE isbn = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Set the new values
        pstmt.setString(1, book.getTitle());
        pstmt.setString(2, book.getAuthor());
        pstmt.setInt(3, book.getPublishYear());
        pstmt.setString(4, book.getGenre());
        
        // get the isbn to get the right book to delete
        pstmt.setString(5, book.getIsbn());

        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("No book found with ISBN: " + book.getIsbn());
        }

    } catch (SQLException e) {
        System.out.println("Error updating book: " + e.getMessage());
    }
    
    }
    
    public void deleteBook(String isbn) {
    String sql = "DELETE FROM books WHERE isbn = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Set the ISBN to want to delete
        pstmt.setString(1, isbn);

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Book with ISBN " + isbn + " was deleted successfully.");
        } else {
            System.out.println("Delete failed: No book found with ISBN " + isbn);
        }

    } catch (SQLException e) {
        System.out.println("Error deleting book: " + e.getMessage());
    }
}
}
