package com.workflow.core;

/**
 * Represents a user in the workflow system with authentication credentials and role.
 */
public class User {
    private String username;
    private String password;
    private String role; // e.g., Junior, Manager, Senior

    public User(String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be empty");
        }
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}