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
    String taskName;
    String newStatus;

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

        System.out.println("\nGoodbye! Thank you for using the Jeff's To-Do List!");
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
            case "4":
                this.displayStatusStats();
                break;
            case "5":
                this.changeTaskStatus();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    private void displayStatusStats() {
        System.out.print("Tasks Not Started: " + toDoList.getNumberOfTasksNotStarted() + "\n");
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
        int counter = 1;
        for (TaskItem ti : toDoList.getToDoList()) {
            System.out.println("Task " + counter + ": " + ti.getTaskName() + " / Status: " + ti.getStatus());
            counter++;
        }
    }

    // EFFECTS: Prints text to indicate input String and TaskItem name must match exactly.
    private boolean displayTextExactMatch() {
        if (toDoList.getToDoListSize() == 0) {
            System.out.print("There are no tasks in the current list. \n");
            return false;
        } else {
            System.out.print("Your input must match the task name exactly (including spacing and case). \n");
            System.out.print("Otherwise, the task may not be found properly. ");
            System.out.print("Enter the task name: \n");
            taskName = input.next();
            return true;
        }
    }

    // EFFECTS: Removes a Task
    private void removeTask() {
        if (displayTextExactMatch()) {
            int pos = toDoList.taskPosition(taskName);
            if (pos == -1) {
                System.out.print("No Matching Task. Returning to the Welcome Screen... \n");
            } else {
                toDoList.removeTask(pos);
                System.out.print("Task Removed. Returning to the Welcome Screen... \n");
            }
        }
    }

    // EFFECTS: Changes Status of a TaskItem
    private void changeTaskStatus() {
        if (displayTextExactMatch()) {
            int pos = toDoList.taskPosition(taskName);
            if (pos == -1) {
                System.out.print("No Matching Task \n");
            } else {
                System.out.print("Enter '1' for Not Started, '2' for In Progress, and '3' for Completed. \n");
                newStatus = input.next();
            }
            switch (newStatus) {
                case "1":
                    toDoList.getTaskItem(pos).changeTaskStatusToNotStarted();
                    break;
                case "3":
                    toDoList.getTaskItem(pos).changeTaskStatusToCompleted();
                    break;
                case "2":
                    toDoList.getTaskItem(pos).changeTaskStatusToInProgress();
                    break;
                default:
                    System.out.print("Not a Valid Status\n");
                    break;
            }
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
        System.out.println("- '4' to get task status stats");
        System.out.println("- '5' to change task status");
        System.out.println("- 'q' to quit");
    }
}
