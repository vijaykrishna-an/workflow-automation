package com.workflow.ui;

import com.workflow.core.Task;
import com.workflow.core.User;
import com.workflow.services.AuthenticationService;
import com.workflow.services.NotificationService;
import com.workflow.services.WorkflowService;

import java.util.Scanner;

/**
 * Console interface for user interaction with the workflow automation system.
 */
public class ConsoleInterface {
    private Scanner scanner = new Scanner(System.in);
    private User currentUser;
    private AuthenticationService authService = new AuthenticationService();
    private WorkflowService workflowService = new WorkflowService();
    private NotificationService notificationService = new NotificationService();

    public void displayMainMenu() {
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                displayLoginMenu();
            } else {
                displayLoggedInMenu();
            }
        }
        scanner.close();
    }

    private void displayLoginMenu() {
        System.out.println("\n=== Workflow Automation System ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");
        String input = scanner.nextLine();
        try {
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error: Invalid option");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a number");
        }
    }

    private void displayLoggedInMenu() {
        System.out.println("\n=== Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ") ===");
        System.out.println("1. Create Task");
        System.out.println("2. List Tasks");
        System.out.println("3. Process Task (Approve/Reject)");
        System.out.println("4. Rollback Task");
        System.out.println("5. Logout");
        System.out.print("Choose option: ");
        String input = scanner.nextLine();
        try {
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1:
                    createTask();
                    break;
                case 2:
                    listTasks();
                    break;
                case 3:
                    processTask();
                    break;
                case 4:
                    rollbackTask();
                    break;
                case 5:
                    currentUser = null;
                    System.out.println("Logged out");
                    break;
                default:
                    System.out.println("Error: Invalid option");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a number");
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        currentUser = authService.login(username, password);
    }

    private void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Role (Junior/Manager/Senior): ");
        String role = scanner.nextLine().trim();
        authService.register(username, password, role);
    }

    private void createTask() {
        if (currentUser == null) {
            System.out.println("Error: Must be logged in");
            return;
        }
        System.out.print("Task description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Priority (1=Low, 2=Medium, 3=High): ");
        try {
            int priority = Integer.parseInt(scanner.nextLine().trim());
            Task task = workflowService.createTask(description, priority, currentUser);
            if (task != null) {
                notificationService.attachObserver(task, currentUser);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Priority must be a number");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listTasks() {
        if (currentUser == null) {
            System.out.println("Error: Must be logged in");
            return;
        }
        System.out.println("Tasks:");
        for (Task task : workflowService.getTasks()) {
            System.out.println("- ID: " + task.getId() + ", Description: " + task.getDescription() +
                    ", Priority: " + task.getPriority() + ", Status: " + task.getStatus());
        }
    }

    private void processTask() {
        if (currentUser == null) {
            System.out.println("Error: Must be logged in");
            return;
        }
        System.out.print("Task ID: ");
        String taskId = scanner.nextLine().trim();
        Task task = workflowService.getTask(taskId);
        if (task == null) {
            System.out.println("Error: Task not found");
            return;
        }
        // Check if user role matches task priority or allow manual override
        boolean canApprove = (task.getPriority() == 1 && currentUser.getRole().equals("Junior")) ||
                (task.getPriority() == 2 && currentUser.getRole().equals("Manager")) ||
                (task.getPriority() == 3 && currentUser.getRole().equals("Senior"));
        System.out.print("Action (1=Approve, 2=Reject): ");
        String actionInput = scanner.nextLine().trim();
        try {
            int action = Integer.parseInt(actionInput);
            if (action == 1 && !canApprove) {
                System.out.println("Error: User role " + currentUser.getRole() + " cannot approve priority " + task.getPriority());
                return;
            }
            String rejectionReason = null;
            if (action == 2) {
                System.out.print("Enter rejection reason (optional): ");
                rejectionReason = scanner.nextLine().trim();
            }
            if (action == 1 || action == 2) {
                workflowService.processTask(task, action == 1, rejectionReason);
            } else {
                System.out.println("Error: Invalid action");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Action must be a number (1 or 2)");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void rollbackTask() {
        if (currentUser == null) {
            System.out.println("Error: Must be logged in");
            return;
        }
        System.out.print("Task ID: ");
        String taskId = scanner.nextLine().trim();
        Task task = workflowService.getTask(taskId);
        if (task == null) {
            System.out.println("Error: Task not found");
            return;
        }
        try {
            workflowService.rollbackTask(task);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
