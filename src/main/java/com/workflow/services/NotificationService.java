package com.workflow.services;

import com.workflow.core.Task;
import com.workflow.core.User;
import com.workflow.patterns.observer.TaskObserver;
import com.workflow.patterns.observer.UserObserver;

/**
 * Manages observer attachments for task notifications.
 */
public class NotificationService {
    public void attachObserver(Task task, User user) {
        if (task == null || user == null) {
            System.out.println("Error: Task or user cannot be null");
            return;
        }
        task.attach(new UserObserver(user));
        System.out.println("Observer attached for user: " + user.getUsername());
    }
}