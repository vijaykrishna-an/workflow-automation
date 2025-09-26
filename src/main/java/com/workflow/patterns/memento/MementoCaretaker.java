package com.workflow.patterns.memento;

import java.util.Stack;

/**
 * Caretaker for managing task state snapshots.
 */
public class MementoCaretaker {
    private Stack<TaskMemento> mementos = new Stack<>();

    public void save(TaskOriginator originator) {
        if (originator == null) {
            throw new IllegalArgumentException("Originator cannot be null");
        }
        mementos.push(originator.save());
    }

    public void restore(TaskOriginator originator) {
        if (originator == null) {
            throw new IllegalArgumentException("Originator cannot be null");
        }
        if (mementos.isEmpty()) {
            throw new IllegalStateException("No snapshots to restore");
        }
        originator.restore(mementos.pop());
    }
}