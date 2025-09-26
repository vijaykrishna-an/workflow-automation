package com.workflow.patterns.observer;

import com.workflow.core.User;

/**
 * Observer for users to receive task notifications.
 */
public class UserObserver implements TaskObserver {
    private User user;

    public UserObserver(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    @Override
    public void update(String event) {
        System.out.println("Notification for " + user.getUsername() + " (" + user.getRole() + "): " + event);
    }
}