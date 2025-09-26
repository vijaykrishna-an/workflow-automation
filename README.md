```markdown
# Decentralized Workflow Automation for Remote Teams

## Overview

This is a console-based Java application designed to simulate a decentralized workflow automation system for remote teams. It enables users to create, process, and manage tasks (e.g., code reviews, expense approvals) with role-based routing, real-time notifications, and state rollback capabilities. The application leverages three behavioral design patterns:

- **Chain of Responsibility**: Routes tasks to appropriate approvers (Junior, Manager, Senior) based on task priority.
- **Observer**: Notifies users of task status changes in real-time.
- **Memento**: Saves task states for rollback to previous states (e.g., undoing a rejection).

The system operates entirely in-memory, without external database dependencies, making it lightweight and suitable for demonstrating sophisticated software design principles in a console environment.

## Features

- **User Authentication**: Register and log in with username, password, and role (Junior, Manager, Senior).
- **Task Management**: Create tasks with descriptions and priorities (1=Low, 2=Medium, 3=High).
- **Task Processing**: Approve or reject tasks, with role-based approval validation and optional rejection reasons.
- **Real-Time Notifications**: Users receive updates on task status changes.
- **State Rollback**: Revert tasks to previous states using the Memento pattern.
- **In-Memory Data**: Tasks and user data stored in efficient data structures (HashMap, Stack, ArrayList).

## Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher (tested with JDK 17).
- **Maven** (optional): For simplified build and execution.
- **Operating System**: Compatible with Windows, macOS, or Linux.

Verify JDK installation:
```bash
java -version
javac -version
```

## Project Structure

```
workflow-automation/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── workflow/
│                   ├── core/
│                   │   ├── Task.java
│                   │   └── User.java
│                   ├── patterns/
│                   │   ├── chainofresponsibility/
│                   │   │   ├── ApproverHandler.java
│                   │   │   ├── JuniorHandler.java
│                   │   │   ├── ManagerHandler.java
│                   │   │   └── SeniorHandler.java
│                   │   ├── memento/
│                   │   │   ├── TaskMemento.java
│                   │   │   ├── TaskOriginator.java
│                   │   │   └── MementoCaretaker.java
│                   │   ├── observer/
│                   │   │   ├── TaskObserver.java
│                   │   │   ├── TaskSubject.java
│                   │   │   └── UserObserver.java
│                   ├── services/
│                   │   ├── AuthenticationService.java
│                   │   ├── NotificationService.java
│                   │   └── WorkflowService.java
│                   ├── ui/
│                   │   └── ConsoleInterface.java
│                   └── Main.java
├── pom.xml
├── create_project.sh
└── README.md
```

- **core/**: Domain models (`Task`, `User`).
- **patterns/**: Implementations of Chain of Responsibility, Memento, and Observer patterns.
- **services/**: Business logic for authentication, notifications, and workflow management.
- **ui/**: Console-based user interface.
- **Main.java**: Application entry point.
- **pom.xml**: Maven configuration for building the project.
- **create_project.sh**: Script to set up directory structure (Unix-like systems).

## Setup Instructions

1. **Clone or Unzip the Project**:
    - If you have the ZIP file (`workflow-automation.zip`), extract it:
      ```bash
      unzip workflow-automation.zip
      cd workflow-automation
      ```

2. **Compile the Project**:
    - **Using Maven** (recommended):
      ```bash
      mvn clean compile
      ```
    - **Using javac**:
      ```bash
      javac -d target/classes src/main/java/com/workflow/**/*.java
      ```
      For Windows:
      ```bash
      javac -d target\classes src\main\java\com\workflow\*.java src\main\java\com\workflow\core\*.java src\main\java\com\workflow\services\*.java src\main\java\com\workflow\ui\*.java src\main\java\com\workflow\patterns\chainofresponsibility\*.java src\main\java\com\workflow\patterns\memento\*.java src\main\java\com\workflow\patterns\observer\*.java
      ```

3. **Run the Application**:
    - **Using Maven**:
      ```bash
      mvn exec:java -Dexec.mainClass="com.workflow.Main"
      ```
    - **Using java**:
      ```bash
      java -cp target/classes com.workflow.Main
      ```

## Usage

1. **Start the Application**:
    - Run the application as described above. You’ll see the main menu:
      ```
      === Workflow Automation System ===
      1. Login
      2. Register
      3. Exit
      Choose option:
      ```

2. **Register a User**:
    - Select option 2 to register:
      ```
      Username: user1
      Password: password123
      Role (Junior/Manager/Senior): Manager
      ```
    - Output: `User registered: user1`

3. **Log In**:
    - Select option 1 to log in with the registered credentials:
      ```
      Username: user1
      Password: password123
      ```
    - Output: `Login successful: user1`

4. **Logged-In Menu**:
    - After login, you’ll see:
      ```
      === Welcome, user1 (Manager) ===
      1. Create Task
      2. List Tasks
      3. Process Task (Approve/Reject)
      4. Rollback Task
      5. Logout
      Choose option:
      ```

5. **Create a Task**:
    - Select option 1:
      ```
      Task description: Review code changes
      Priority (1=Low, 2=Medium, 3=High): 2
      ```
    - Output: `Task created: abc12345` and `Observer attached for user: user1`

6. **List Tasks**:
    - Select option 2:
      ```
      Tasks:
      - ID: abc12345, Description: Review code changes, Priority: 2, Status: Pending
      ```

7. **Process Task (Approve/Reject)**:
    - Select option 3:
      ```
      Task ID: abc12345
      Action (1=Approve, 2=Reject): 2
      Enter rejection reason (optional): Needs more details
      ```
    - Output:
      ```
      Task abc12345 rejected with reason: Needs more details
      Notification for user1 (Manager): Task abc12345 status updated to: Rejected: Needs more details
      ```
    - For approval (if role matches priority):
      ```
      Action (1=Approve, 2=Reject): 1
      Manager Handler processing task: abc12345
      Notification for user1 (Manager): Task abc12345 status updated to: Approved by Manager
      ```

8. **Rollback Task**:
    - Select option 4 to revert to the previous state:
      ```
      Task ID: abc12345
      ```
    - Output:
      ```
      Task abc12345 rolled back to previous state
      Notification for user1 (Manager): Task abc12345 status updated to: Pending
      ```

9. **Logout**:
    - Select option 5 to return to the main menu.

## Design Patterns

- **Chain of Responsibility**: Tasks are routed through `JuniorHandler`, `ManagerHandler`, and `SeniorHandler` based on priority (1, 2, 3). Approval is restricted to users with matching roles.
- **Observer**: `Task` notifies `UserObserver` instances of status changes, ensuring real-time updates.
- **Memento**: `TaskMemento`, `TaskOriginator`, and `MementoCaretaker` save and restore task states for rollback.

## Data Management

- **Tasks**: Stored in a `HashMap<String, Task>` for O(1) access by task ID.
- **Snapshots**: Managed in a `Stack<TaskMemento>` per task for efficient state restoration.
- **Observers**: Stored in an `ArrayList<TaskObserver>` within `Task` for dynamic notifications.
- **Users**: Stored in a `HashMap<String, User>` for authentication.

## Error Handling

- Validates inputs (e.g., non-empty usernames, valid priorities, matching roles for approval).
- Provides clear error messages for invalid inputs or actions (e.g., attempting to approve a task with a mismatched role).

## Troubleshooting

- **Compilation Errors**: Ensure JDK 8+ is installed and all files are in the correct directory structure.
- **ClassNotFoundException**: Verify the classpath (`target/classes`) is set correctly when running with `java`.
- **No Snapshots to Restore**: Ensure the task was processed at least once before attempting rollback.
- **Permission Errors**: Check write permissions for the `target/classes` directory.

## Limitations

- In-memory storage means data is not persisted across sessions.
- Role-based approval is strict (e.g., only Managers can approve priority 2 tasks).
- No support for task reassignment or multi-user collaboration within a session.

## Contributing

To contribute, fork the project, make changes, and submit a pull request with a description of your modifications. Ensure changes align with the existing design patterns and maintain code quality.

## License

This project is licensed under the MIT License. See the LICENSE file for details (not included in this distribution).

---
*Generated on September 26, 2025*
```

### Integration with ZIP File
To include the README in your `workflow-automation.zip`:
1. **Create the README File**:
   - In the `workflow-automation` directory, create a file named `README.md`.
   - Copy the content from the `<xaiArtifact>` block above (excluding the tags) into `README.md`.

2. **Update the ZIP**:
   - If you’ve already created `workflow-automation.zip`, add the README:
     ```bash
     cd workflow-automation
     zip -u workflow-automation.zip README.md
     mv workflow-automation.zip ..
     ```
   - Or recreate the ZIP with all files:
     ```bash
     cd workflow-automation
     zip -r workflow-automation.zip .
     mv workflow-automation.zip ..
     ```
   - On Windows (PowerShell):
     ```powershell
     Compress-Archive -Path workflow-automation/* -DestinationPath workflow-automation.zip -Update
     ```

### Notes
- **Purpose**: The README provides clear instructions for setting up, running, and using the application, making it easy for users to understand and execute the project locally.
- **Content**: Includes an overview, features, setup instructions, usage examples, design pattern explanations, and troubleshooting tips, aligned with standard open-source project documentation.
- **Placement**: The README is placed in the project root (`workflow-automation/`) for visibility.
- **ZIP Update**: The README is a new file, so it should be added to the existing ZIP or included in a new ZIP with all files (as provided in previous responses).
- **Artifact ID**: A new UUID (`7f3a2b8c-4d29-4f1b-9e7a-3c8e4f6a9b2d`) is used since this is a new artifact.

You can now add this README to your project directory, update the ZIP file, and follow the instructions in the README to compile and run the application. If you need further assistance with automating the ZIP creation or have specific requirements, let me know!