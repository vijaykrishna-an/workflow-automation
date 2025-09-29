# Workflow Automation Application

Hey there! This is a console-based Java app that simulates a decentralized workflow automation system for remote teams. It lets you create, process, and manage tasks like code reviews or expense approvals, with features like role-based task routing, real-time notifications, and the ability to roll back task states. It’s lightweight, runs entirely in memory, and uses some cool design patterns to keep things organized. Below, I’ve covered the patterns used, what each class does, and how to compile and run the app on your machine.

Behavioral Patterns Implemented

The app uses three behavioral design patterns to make it flexible and modular:

- Chain of Responsibility: Tasks are routed to the right approver (Junior, Manager, or Senior) based on their priority (1 for Low, 2 for Medium, 3 for High). Each role can only approve tasks matching their level.
- Observer: Tasks notify users in real-time when their status changes, like when a task is approved or rejected.
- Memento: Saves task states so you can roll back to a previous state, like undoing a rejection to make the task pending again.

These patterns keep the code clean and make it easy to add new features later.

Class Descriptions

All classes are in the `com.workflow` package. Here’s what each one does:

- core/Task.java: Represents a task with a description, priority (1=Low, 2=Medium, 3=High), and status (Pending, Approved, Rejected). It holds observers for notifications and mementos for state rollback.
- core/User.java: Represents a user with a username, password, and role (Junior, Manager, Senior). It’s used for authentication and task processing.
- patterns/chainofresponsibility/ApproverHandler.java: An abstract class for handling task approvals in a chain. It defines the logic for passing tasks to the next handler.
- patterns/chainofresponsibility/JuniorHandler.java: Handles approval for low-priority tasks (priority 1). Only Junior users can approve these.
- patterns/chainofresponsibility/ManagerHandler.java: Handles approval for medium-priority tasks (priority 2). Only Manager users can approve these.
- patterns/chainofresponsibility/SeniorHandler.java: Handles approval for high-priority tasks (priority 3). Only Senior users can approve these.
- patterns/memento/TaskMemento.java: Stores a snapshot of a task’s state (status and reason) for rollback.
- patterns/memento/TaskOriginator.java: Manages task state creation and restoration for the Memento pattern.
- patterns/memento/MementoCaretaker.java: Keeps a stack of task mementos to support state rollback.
- patterns/observer/TaskObserver.java: An interface for objects that need task status updates.
- patterns/observer/TaskSubject.java: An interface for tasks to manage observers and send notifications.
- patterns/observer/UserObserver.java: Notifies a user when a task’s status changes (e.g., “Task abc12345 approved”).
- services/AuthenticationService.java: Handles user registration and login, storing users in a HashMap.
- services/NotificationService.java: Manages sending status updates to users via the Observer pattern.
- services/WorkflowService.java: Orchestrates task creation, processing (approve/reject), and rollback, tying together the patterns.
- ui/ConsoleInterface.java: Provides the console-based UI, showing menus and handling user input for all actions.
- Main.java: The starting point. It launches the app, shows the main menu (login, register, exit), and drives the workflow.

Prerequisites for Compilation and Execution

To get the app running, you’ll need:

- Java Development Kit (JDK): Version 8 or higher (tested with JDK 17). Check with `java -version`.
- Apache Maven: Version 3.6.0 or higher for easy building. Check with `mvn -version`. Download from the Apache Maven website if needed.
- Command Prompt/Terminal: Use Command Prompt on Windows (since your path is `C:\Users\ANVK\Desktop\workflow-automation`).
- Project Files: Ensure all source files are in `src/main/java/com/workflow` as shown in the structure below.

The project structure should look like this:

```
workflow-automation/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── workflow/
│   │               ├── core/
│   │               │   ├── Task.java
│   │               │   └── User.java
│   │               ├── patterns/
│   │               │   ├── chainofresponsibility/
│   │               │   │   ├── ApproverHandler.java
│   │               │   │   ├── JuniorHandler.java
│   │               │   │   ├── ManagerHandler.java
│   │               │   │   └── SeniorHandler.java
│   │               │   ├── memento/
│   │               │   │   ├── TaskMemento.java
│   │               │   │   ├── TaskOriginator.java
│   │               │   │   └── MementoCaretaker.java
│   │               │   ├── observer/
│   │               │   │   ├── TaskObserver.java
│   │               │   │   ├── TaskSubject.java
│   │               │   │   └── UserObserver.java
│   │               ├── services/
│   │               │   ├── AuthenticationService.java
│   │               │   ├── NotificationService.java
│   │               │   └── WorkflowService.java
│   │               ├── ui/
│   │               │   └── ConsoleInterface.java
│   │               └── Main.java
├── pom.xml
└── README.md
```

Compilation and Execution Illustration

Here’s how to compile and run the app step-by-step. These commands are for Windows Command Prompt (using backslashes `\`). Replace `YourUsername` with your actual username (e.g., `ANVK`).

1. Open Command Prompt:
   - Press `Win + R`, type `cmd`, and hit Enter to open Command Prompt.

2. Navigate to Project Root:
   - Move to the project folder:
     ```
     cd C:\Users\YourUsername\Desktop\workflow-automation
     ```

3. Clean Previous Build Artifacts:
   - Clear out old compiled files to start fresh:
     ```
     mvn clean
     ```
   - You’ll see:
     ```
     [INFO] Deleting C:\Users\YourUsername\Desktop\workflow-automation\target
     [INFO] BUILD SUCCESS
     ```

4. Compile the Application:
   - Compile all source files:
     ```
     mvn compile
     ```
   - Expected output:
     ```
     [INFO] Compiling X source files to C:\Users\YourUsername\Desktop\workflow-automation\target\classes
     [INFO] BUILD SUCCESS
     ```
   - This creates `.class` files in `target\classes\com\workflow`.

5. Run the Application:
   - Start the app:
     ```
     mvn exec:java -Dexec.mainClass="com.workflow.Main"
     ```
   - The app begins with:
     ```
     === Workflow Automation System ===
     1. Login
     2. Register
     3. Exit
     Choose option:
     ```
   - Follow prompts to register, log in, create/process tasks, or roll back states (e.g., enter `2` to register, then `1` to log in).

6. Automate with a Batch File (Optional):
   - Create a file called `run.bat` in the project root using Notepad:
     ```
     @echo off
     mvn clean
     mvn compile
     mvn exec:java -Dexec.mainClass="com.workflow.Main"
     ```
   - Save and run it:
     ```
     run.bat
     ```
   - This runs all steps (clean, compile, execute) in one go.

7. What to Expect When Running:
   - Register a user with a username, password, and role (Junior, Manager, Senior).
   - Log in to access the menu: create tasks (e.g., “Review code changes”, priority 1-3), list tasks, process tasks (approve/reject), roll back tasks, or log out.
   - Example task creation:
     ```
     Task description: Review code changes
     Priority (1=Low, 2=Medium, 3=High): 2
     Task created: abc12345
     ```
   - Example task rejection:
     ```
     Task ID: abc12345
     Action (1=Approve, 2=Reject): 2
     Enter rejection reason (optional): Needs more details
     Task abc12345 rejected with reason: Needs more details
     ```
   - Invalid inputs (e.g., wrong role for task approval) show clear errors like: `Permission denied: Role mismatch`.

Troubleshooting

- Compilation Fails:
  - Check that all source files are in `src/main/java/com/workflow`.
  - Ensure `pom.xml` has the Maven Compiler and Exec plugins for Java 17.
  - Run with debug to see details:
    ```
    mvn compile -e -X
    ```
- Maven or Java Missing:
  - Install JDK 17+ and set JAVA_HOME (e.g., `set JAVA_HOME=C:\Program Files\Java\jdk-17`).
  - Install Maven and add its `bin` folder to PATH (e.g., `C:\Program Files\Maven\bin`).
- Runtime Issues:
  - If the app crashes or loops, verify all source files match the described structure.
  - Run with debug:
    ```
    mvn exec:java -Dexec.mainClass="com.workflow.Main" -X
    ```
- File Issues:
  - Use a text editor (e.g., Notepad++, VS Code) to check for invisible characters (use UTF-8 encoding, no BOM).

That’s it! You’re ready to compile and run the Workflow Automation app. Have fun managing tasks!
