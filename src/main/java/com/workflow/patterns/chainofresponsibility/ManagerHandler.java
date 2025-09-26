package com.workflow.patterns.chainofresponsibility;

import com.workflow.core.Task;

/**
 * Handles medium-priority tasks (priority 2).
 */
public class ManagerHandler extends ApproverHandler {
    @Override
    public void handle(Task task) {
        if (task.getPriority() == 2) {
            System.out.println("Manager Handler processing task: " + task.getId());
            task.setStatus("Approved by Manager");
        } else if (next != null) {
            next.handle(task);
        } else {
            System.out.println("No handler available for task: " + task.getId());
            task.setStatus("Rejected: No suitable approver");
        }
    }
}