package com.workflow.patterns.observer;

/**
 * Interface for subjects in the Observer pattern to manage observers.
 */
public interface TaskSubject {
    void attach(TaskObserver observer);
    void detach(TaskObserver observer);
    void notifyObservers(String event);
}