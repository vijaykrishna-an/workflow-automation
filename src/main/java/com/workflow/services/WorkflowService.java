package com.workflow.services;

import com.workflow.core.Task;
import com.workflow.core.User;
import com.workflow.patterns.chainofresponsibility.ApproverHandler;
import com.workflow.patterns.chainofresponsibility.JuniorHandler;
import com.workflow.patterns.chainofresponsibility.ManagerHandler;
import com.workflow.patterns.chainofresponsibility.SeniorHandler;
import com.workflow.patterns.memento.MementoCaretaker;
import com.workflow.patterns.memento.TaskOriginator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages task creation and processing using Chain of Responsibility and Memento patterns.
 */
public class WorkflowService {
    private Map<String, Task> tasks = new HashMap<>();
    private Map<String, MementoCaretaker> caretakers = new HashMap<>();
    private ApproverHandler chain;

    public WorkflowService() {
        // Set up chain of responsibility
        chain = new JuniorHandler();
        chain.setNext(new ManagerHandler());
        chain.setNext(new SeniorHandler());
    }

    public Task createTask(String description, int priority, User creator) {
        if (description == null || description.trim().isEmpty()) {
            System.out.println("Error: Task description cannot be empty");
            return null;
        }
        if (priority < 1 || priority > 3) {
            System.out.println("Error: Priority must be between 1 and 3");
            return null;
        }
        if (creator == null) {
            System.out.println("Error: Creator cannot be null");
            return null;
        }
        Task task = new Task(description, priority);
        tasks.put(task.getId(), task);
        caretakers.put(task.getId(), new MementoCaretaker());
        System.out.println("Task created: " + task.getId());
        return task;
    }

    public void processTask(Task task, boolean approve, String rejectionReason) {
        if (task == null) {
            System.out.println("Error: Task cannot be null");
            return;
        }
        MementoCaretaker caretaker = caretakers.get(task.getId());
        if (caretaker == null) {
            System.out.println("Error: Task not found");
            return;
        }
        caretaker.save(new TaskOriginator(task));
        if (approve) {
            chain.handle(task);
        } else {
            String status = "Rejected";
            if (rejectionReason != null && !rejectionReason.trim().isEmpty()) {
                status += ": " + rejectionReason;
            }
            task.setStatus(status);
            System.out.println("Task " + task.getId() + " rejected" +
                    (rejectionReason != null && !rejectionReason.isEmpty() ? " with reason: " + rejectionReason : ""));
        }
    }

    public void rollbackTask(Task task) {
        if (task == null) {
            System.out.println("Error: Task cannot be null");
            return;
        }
        MementoCaretaker caretaker = caretakers.get(task.getId());
        if (caretaker == null) {
            System.out.println("Error: Task not found");
            return;
        }
        try {
            caretaker.restore(new TaskOriginator(task));
            System.out.println("Task " + task.getId() + " rolled back to previous state");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTask(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            System.out.println("Error: Task ID cannot be empty");
            return null;
        }
        return tasks.get(taskId);
    }
}