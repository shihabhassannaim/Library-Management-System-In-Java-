package com.mycompany.librarymanagementsystem1;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String role; // "admin" or "member"
    private String memberID; // Only for "member" role, can be empty for admins

    public User(String username, String password, String role, String memberID) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.memberID = memberID;
    }

    // Getters
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getMemberID() { return memberID; }

    // Password validation
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return username + "," + password + "," + role + "," + (memberID != null ? memberID : "");
    }

    // Handle both cases (with or without memberID)
    public static User fromString(String data) {
        String[] parts = data.split(",");

        // Handle case where memberID might be missing (only 3 parts)
        if (parts.length == 3) {
            return new User(parts[0], parts[1], parts[2], ""); // Empty memberID for admin
        } else if (parts.length == 4) {
            return new User(parts[0], parts[1], parts[2], parts[3]); // All 4 parts present
        } else {
            throw new IllegalArgumentException("Invalid user data format. Expected 3 or 4 parts but found: " + parts.length);
        }
    }
}
