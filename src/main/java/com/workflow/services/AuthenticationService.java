package com.workflow.services;

import com.workflow.core.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages user authentication and registration.
 */
public class AuthenticationService {
    private Map<String, User> users = new HashMap<>();

    public void register(String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Error: Username cannot be empty");
            return;
        }
        if (password == null || password.length() < 4) {
            System.out.println("Error: Password must be at least 4 characters");
            return;
        }
        if (role == null || role.trim().isEmpty()) {
            System.out.println("Error: Role cannot be empty");
            return;
        }
        if (users.containsKey(username)) {
            System.out.println("Error: Username already exists");
            return;
        }
        users.put(username, new User(username, password, role));
        System.out.println("User registered: " + username);
    }

    public User login(String username, String password) {
        if (username == null || password == null) {
            System.out.println("Error: Username and password cannot be null");
            return null;
        }
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful: " + username);
            return user;
        }
        System.out.println("Login failed: Invalid credentials");
        return null;
    }
}