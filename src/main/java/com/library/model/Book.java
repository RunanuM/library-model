/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.library.model;

/**
 *
 * @author Emperor_RR
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int publishYear;
    private String genre;
    
    //constructor for creating a book object...no id since it's assigned by the database
    public Book(
            String title, 
            String author, 
            String isbn, 
            int publishYear, 
            String genre){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishYear = publishYear;
        this.genre = genre;
    }
    
    //constructor when loading from the database...id is included
    public Book(
            int id, 
            String title, 
            String author, 
            String isbn, 
            int publishYear, 
            String genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishYear = publishYear;
        this.genre = genre;
    }
    
    public Book(){}
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    //this ensures that correct details of the book will be printed out not the memory location
    @Override
    public String toString() {
        return "Book{" + "id=" + 
                id + ", title=" + title + ", author=" + 
                author + ", isbn=" + isbn + ", publishYear=" + 
                publishYear + ", genre=" + genre + '}';
    }
    
    
}
