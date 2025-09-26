package com.workflow;

import com.workflow.ui.ConsoleInterface;

/**
 * Entry point for the Decentralized Workflow Automation console application.
 */
public class Main {
    public static void main(String[] args) {
        ConsoleInterface console = new ConsoleInterface();
        console.displayMainMenu();
    }
}