package com.workflow.patterns.memento;

/**
 * Memento to store task state (status).
 */
public class TaskMemento {
    private final String status;

    public TaskMemento(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}