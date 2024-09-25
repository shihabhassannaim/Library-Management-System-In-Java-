package com.mycompany.librarymanagementsystem1;

import java.util.Scanner;

public class LibraryManagementSystem1 {
    public static void main(String[] args) {
        Library library = new Library(); // Initialize Library
        Scanner scanner = new Scanner(System.in);

        User loggedInUser = loginOrRegister(library, scanner);
        if (loggedInUser == null) return; // Exit if login or registration fails

        int choice;
        do {
            displayMenu(loggedInUser.getRole());
            choice = getUserChoice(scanner);
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
        if (loggedInUser == null) {
            System.out.print("User not found. Would you like to register? (yes/no): ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes")) {
                return registerUser(library, scanner);
            } else {
                System.out.println("Login failed. Exiting...");
                return null; // Exit if user chooses not to register
            }
        }
        return loggedInUser; // Return the logged-in user
    }

    private static User registerUser(Library library, Scanner scanner) {
        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a new password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (admin/visitor): ");
        String role = scanner.nextLine();

        if (library.getUserByUsername(username) != null) {
            System.out.println("Username already exists. Please choose a different username.");
            return null;
        }

        User newUser = new User(username, password, role, null);
        library.registerUser(newUser);
        return library.login(username, password); // Attempt to log in the newly registered user
    }

    private static int getUserChoice(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
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
        switch (choice) {
            case 1:
                if (loggedInUser.getRole().equalsIgnoreCase("admin")) {
                    addBook(library, scanner);
                } else {
                    System.out.println("Insufficient permissions.");
                }
                break;
            case 2:
                if (loggedInUser.getRole().equalsIgnoreCase("admin")) {
                    registerMember(library, scanner);
                } else {
                    System.out.println("Insufficient permissions.");
                }
                break;
            case 3:
                borrowBook(library, scanner);
                break;
            case 4:
                returnBook(library, scanner);
                break;
            case 5:
                searchBook(library, scanner);
                break;
            case 6:
                System.out.println("Exiting the system.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addBook(Library library, Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        library.addBook(new Book(title, author, isbn));
    }

    private static void registerMember(Library library, Scanner scanner) {
        System.out.print("Enter member name: ");
        String memberName = scanner.nextLine();
        String memberId = library.generateMemberId();
        library.registerMember(new Member(memberName, memberId));
    }

    private static void borrowBook(Library library, Scanner scanner) {
        System.out.print("Enter member ID: ");
        String memberID = scanner.nextLine();
        System.out.print("Enter book title to borrow: ");
        String title = scanner.nextLine();
        Member member = library.getMemberById(memberID);
        if (member != null) {
            member.borrowBook(library.searchBookByTitle(title));
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void returnBook(Library library, Scanner scanner) {
        System.out.print("Enter member ID: ");
        String memberID = scanner.nextLine();
        System.out.print("Enter book title to return: ");
        String title = scanner.nextLine();
        Member member = library.getMemberById(memberID);
        if (member != null) {
            member.returnBook(library.searchBookByTitle(title));
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void searchBook(Library library, Scanner scanner) {
        System.out.print("Enter book title to search: ");
        String title = scanner.nextLine();
        Book book = library.searchBookByTitle(title);
        System.out.println(book != null ? "Found: " + book : "Book not found.");
    }
}
