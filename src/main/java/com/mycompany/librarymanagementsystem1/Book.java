package com.mycompany.librarymanagementsystem1;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String isbn;
    private boolean isBorrowed;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isBorrowed = false;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isBorrowed() { return isBorrowed; }

    public void borrowBook() { this.isBorrowed = true; }
    public void returnBook() { this.isBorrowed = false; }

    @Override
    public String toString() {
        return title + "," + author + "," + isbn + "," + isBorrowed;
    }

    public static Book fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid book data format.");
        }
        Book book = new Book(parts[0], parts[1], parts[2]);
        book.isBorrowed = Boolean.parseBoolean(parts[3]);
        return book;
    }
}
