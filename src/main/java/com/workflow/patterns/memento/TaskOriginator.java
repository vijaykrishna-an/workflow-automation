package com.workflow.patterns.memento;

import com.workflow.core.Task;

/**
 * Originator for creating and restoring task state snapshots.
 */
public class TaskOriginator {
    private Task task;

    public TaskOriginator(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        this.task = task;
    }

    public TaskMemento save() {
        return new TaskMemento(task.getStatus());
    }

    public void restore(TaskMemento memento) {
        if (memento == null) {
            throw new IllegalArgumentException("Memento cannot be null");
        }
        task.setStatus(memento.getStatus());
    }
}