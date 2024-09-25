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

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public void borrowBook(Book book) {
        if (book != null && !book.isBorrowed()) {
            borrowedBooks.add(book);
            book.borrowBook();
            System.out.println("You have borrowed: " + book.getTitle());
        } else {
            System.out.println("Sorry, the book is already borrowed or does not exist.");
        }
    }

    public void returnBook(Book book) {
        if (book != null && borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.returnBook();
            System.out.println("You have returned: " + book.getTitle());
        } else {
            System.out.println("This book was not borrowed by you or does not exist.");
        }
    }

    @Override
    public String toString() {
        return name + "," + memberId;
    }

    public static Member fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid member data format.");
        }
        return new Member(parts[0], parts[1]);
    }
}
