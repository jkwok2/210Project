package ui;

import model.TaskItem;
import model.ToDoList;

import java.util.Arrays;
import java.util.Scanner;

public class ToDoApp {
    //fields
    private Scanner input;
    private ToDoList toDoList;
    private TaskItem taskItem;

    // EFFECTS: runs the teller application
    public ToDoApp() {
        runList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runList() {
        boolean runStatus = true;
        String command;
        init();
        while (runStatus) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                runStatus = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "1":
                this.addTask();
                break;
            case "2":
                this.displayToDoList();
                break;
            case "3":
                this.removeTask();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // EFFECTS: Adds a task
    private void addTask() {
        System.out.print("Enter Task name: ");
        taskItem = new TaskItem();
        String taskName;
        taskName = input.next();
        taskItem.addTaskName(taskName);
        System.out.print("Enter description of the task: ");
        String description;
        description = input.next();
        taskItem.addDescription(description);
        System.out.print("You have added the task: ");
        toDoList.addTask(taskItem);
        System.out.print(taskItem.getTaskName() + "\n");
    }

    // EFFECTS: Displays ToDoList
    private void displayToDoList() {
        for (TaskItem ti : toDoList.getToDoList()) {
            System.out.println(ti.getTaskName());
        }
    }

    // EFFECTS: Removes a Task
    private void removeTask() {
        System.out.print("Enter the task name to remove it. \n");
        System.out.print("If the task does not match any existing task, there will be an error. \n");
        String taskName;
        taskName = input.next();
        int pos = toDoList.taskPosition(taskName);
        if (pos == -1) {
            System.out.print("No Matching Task \n");
        } else {
            toDoList.removeTask(pos);
            System.out.print("Task Removed \n");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        toDoList = new ToDoList();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Welcome! Enter:");
        System.out.println("- '1' to add a task");
        System.out.println("- '2' to view tasks");
        System.out.println("- '3' to delete a task");
        System.out.println("- 'q' to quit");
    }
}
