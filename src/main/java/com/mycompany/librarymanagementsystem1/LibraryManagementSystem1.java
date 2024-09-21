package com.mycompany.librarymanagementsystem1;

import java.util.Scanner;

public class LibraryManagementSystem1 {
    public static void main(String[] args) {
        Library library = new Library(); // Initialize Library
        Scanner scanner = new Scanner(System.in);

        // Login or Registration
        User loggedInUser = loginOrRegister(library, scanner);
        if (loggedInUser == null) return; // Exit if login or registration fails

        // Main menu loop
        int choice;
        do {
            displayMenu(loggedInUser.getRole());
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            handleChoice(choice, loggedInUser, library, scanner);
        } while (choice != 6);

        scanner.close(); // Close the scanner to prevent resource leaks
    }

    private static User loginOrRegister(Library library, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = library.login(username, password);

        // Check if user exists
        if (loggedInUser == null) {
            System.out.print("User not found. Would you like to register? (yes/no): ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes")) {
                System.out.print("Enter a new username: ");
                username = scanner.nextLine();
                System.out.print("Enter a new password: ");
                password = scanner.nextLine();
                System.out.print("Enter role (admin/member): ");
                String role = scanner.nextLine();

                // Check if the username already exists
                if (library.getUserByUsername(username) != null) {
                    System.out.println("Username already exists. Please choose a different username.");
                    return null;
                }

                // Register the user
                library.registerUser(new User(username, password, role, null));

                // Attempt to log in the newly registered user
                loggedInUser = library.login(username, password);
                if (loggedInUser == null) {
                    System.out.println("Registration failed. Exiting...");
                    return null;
                } else {
                    System.out.println("Registration successful! Welcome, " + loggedInUser.getUsername());
                }
            } else {
                System.out.println("Login failed. Exiting...");
                return null; // Exit if user chooses not to register
            }
        }
        return loggedInUser; // Return the logged-in user
    }

    private static void displayMenu(String role) {
        System.out.println("\nLibrary Management System");
        if (role.equalsIgnoreCase("admin")) {
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
        }
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Book");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleChoice(int choice, User loggedInUser, Library library, Scanner scanner) {
        if (choice == 1 && loggedInUser.getRole().equalsIgnoreCase("admin")) {
            // Add Book (only admin)
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();
            System.out.print("Enter book author: ");
            String author = scanner.nextLine();
            System.out.print("Enter book ISBN: ");
            String isbn = scanner.nextLine();
            library.addBook(new Book(title, author, isbn));
        } else if (choice == 2 && loggedInUser.getRole().equalsIgnoreCase("admin")) {
            // Register Member (only admins)
            System.out.print("Enter member name: ");
            String memberName = scanner.nextLine();
            String memberId = library.generateMemberId(); // Generate a unique member ID
            library.registerMember(new Member(memberName, memberId)); // Use the generated ID
        } else if (choice == 3) {
            // Borrow Book
            System.out.print("Enter member ID: ");
            String memberID = scanner.nextLine();
            System.out.print("Enter book title to borrow: ");
            String title = scanner.nextLine();
            library.getMemberById(memberID).borrowBook(library.searchBookByTitle(title));
        } else if (choice == 4) {
            // Return Book
            System.out.print("Enter member ID: ");
            String memberID = scanner.nextLine();
            System.out.print("Enter book title to return: ");
            String title = scanner.nextLine();
            library.getMemberById(memberID).returnBook(library.searchBookByTitle(title));
        } else if (choice == 5) {
            // Search Book
            System.out.print("Enter book title to search: ");
            String title = scanner.nextLine();
            Book book = library.searchBookByTitle(title);
            System.out.println(book != null ? "Found: " + book : "Book not found.");
        } else if (choice != 6) {
            System.out.println("Invalid choice or insufficient permissions.");
        } else {
            System.out.println("Exiting the system.");
        }
    }
}
