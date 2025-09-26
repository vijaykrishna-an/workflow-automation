package com.workflow.core;

import com.workflow.patterns.observer.TaskObserver;
import com.workflow.patterns.observer.TaskSubject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a task with description, priority, and state, implementing Observer pattern.
 */
public class Task implements TaskSubject {
    private String id;
    private String description;
    private int priority; // 1 (low) to 3 (high)
    private String status; // e.g., Pending, Approved, Rejected
    private List<TaskObserver> observers = new ArrayList<>();

    public Task(String description, int priority) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }
        if (priority < 1 || priority > 3) {
            throw new IllegalArgumentException("Priority must be between 1 and 3");
        }
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.description = description;
        this.priority = priority;
        this.status = "Pending";
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        this.status = status;
        notifyObservers("Task " + id + " status updated to: " + status);
    }

    @Override
    public void attach(TaskObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(TaskObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (TaskObserver observer : new ArrayList<>(observers)) {
            observer.update(event);
        }
    }
}