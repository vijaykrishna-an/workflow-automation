package com.workflow.patterns.chainofresponsibility;

import com.workflow.core.Task;

/**
 * Handles low-priority tasks (priority 1).
 */
public class JuniorHandler extends ApproverHandler {
    @Override
    public void handle(Task task) {
        if (task.getPriority() == 1) {
            System.out.println("Junior Handler processing task: " + task.getId());
            task.setStatus("Approved by Junior");
        } else if (next != null) {
            next.handle(task);
        } else {
            System.out.println("No handler available for task: " + task.getId());
            task.setStatus("Rejected: No suitable approver");
        }
    }
}