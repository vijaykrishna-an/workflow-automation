package com.workflow.patterns.chainofresponsibility;

import com.workflow.core.Task;

/**
 * Handles high-priority tasks (priority 3).
 */
public class SeniorHandler extends ApproverHandler {
    @Override
    public void handle(Task task) {
        if (task.getPriority() == 3) {
            System.out.println("Senior Handler processing task: " + task.getId());
            task.setStatus("Approved by Senior");
        } else {
            System.out.println("No handler available for task: " + task.getId());
            task.setStatus("Rejected: No suitable approver");
        }
    }
}