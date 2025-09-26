package com.workflow.patterns.observer;

/**
 * Interface for Observer pattern to receive task updates.
 */
public interface TaskObserver {
    void update(String event);
}