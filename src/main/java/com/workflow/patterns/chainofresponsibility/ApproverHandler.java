package com.workflow.patterns.chainofresponsibility;

import com.workflow.core.Task;

/**
 * Abstract handler for Chain of Responsibility pattern to process tasks.
 */
public abstract class ApproverHandler {
    protected ApproverHandler next;

    public void setNext(ApproverHandler next) {
        this.next = next;
    }

    public abstract void handle(Task task);
}