package com.mycompany.librarymanagementsystem1;

import java.io.*;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<User> users;
    private final String booksFile = "books.txt";
    private final String membersFile = "members.txt";
    private final String usersFile = "users.txt";
    private static int memberCounter = 1;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        users = new ArrayList<>();

        ensureFileExists(booksFile);
        ensureFileExists(membersFile);
        ensureFileExists(usersFile);

        loadBooks();
        loadMembers();
        loadUsers();
    }

    private void ensureFileExists(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists() && file.createNewFile()) {
                System.out.println("File created: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("Error ensuring file exists: " + e.getMessage());
        }
    }

    public String generateMemberId() {
        return "M" + memberCounter++;
    }

    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(User.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing user data: " + e.getMessage());
        }
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                return user;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    private void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader(booksFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                books.add(Book.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing book data: " + e.getMessage());
        }
    }

    private void loadMembers() {
        try (BufferedReader br = new BufferedReader(new FileReader(membersFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                members.add(Member.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading members: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing member data: " + e.getMessage());
        }
    }

    public void registerUser(User user) {
        if (getUserByUsername(user.getUsername()) == null) {
            users.add(user);
            saveUsers();
            System.out.println("User registered successfully: " + user.getUsername());
        } else {
            System.out.println("Username already exists.");
        }
    }

    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile))) {
            for (User user : users) {
                bw.write(user.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void registerMember(Member member) {
        members.add(member);
        saveMembers();
        System.out.println("Member registered: " + member.getName() + ", ID: " + member.getMemberId());
    }

    private void saveMembers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(membersFile))) {
            for (Member member : members) {
                bw.write(member.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        System.out.println("Book not found.");
        return null;
    }

    public Member getMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        System.out.println("Member not found.");
        return null;
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
        System.out.println("Book added: " + book.getTitle());
    }

    private void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile))) {
            for (Book book : books) {
                bw.write(book.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }
}
