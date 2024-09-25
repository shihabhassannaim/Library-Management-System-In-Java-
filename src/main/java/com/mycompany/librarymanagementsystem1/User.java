package com.mycompany.librarymanagementsystem1;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String role;
    private String memberID;

    public User(String username, String password, String role, String memberID) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.memberID = memberID;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getMemberID() { return memberID; }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return username + "," + password + "," + role + "," + (memberID != null ? memberID : "");
    }

    public static User fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length < 3 || parts.length > 4) {
            throw new IllegalArgumentException("Invalid user data format.");
        }
        return new User(parts[0], parts[1], parts[2], parts.length == 4 ? parts[3] : ""); // Handle optional memberID
    }
}
