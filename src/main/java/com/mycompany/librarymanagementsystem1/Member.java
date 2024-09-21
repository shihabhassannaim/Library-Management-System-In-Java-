package com.mycompany.librarymanagementsystem1;

import java.io.Serializable;
import java.util.ArrayList;

public class Member implements Serializable {
    private String name;
    private String memberId;
    private ArrayList<Book> borrowedBooks;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public void borrowBook(Book book) {
        if (!book.isBorrowed()) {
            borrowedBooks.add(book);
            book.borrowBook();
            System.out.println("You have borrowed: " + book.getTitle());
        } else {
            System.out.println("Sorry, the book is already borrowed.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.returnBook();
            System.out.println("You have returned: " + book.getTitle());
        } else {
            System.out.println("This book was not borrowed by you.");
        }
    }

    @Override
    public String toString() {
        return name + "," + memberId; // Ensure saving the correct format
    }

    // Static method to create a Member from a string
    public static Member fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length == 2) {
            return new Member(parts[0], parts[1]);
        } else {
            throw new IllegalArgumentException("Invalid member data format. Expected 2 parts but found: " + parts.length);
        }
    }
}
