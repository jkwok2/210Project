package ui;

import model.TaskItem;
import model.ToDoList;

import java.util.Scanner;

public class ToDoApp {
    //fields
    private Scanner input;
    private ToDoList toDoList;
    private TaskItem taskItem;
    String taskName;
    String newStatus;
    String taskNumber;

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
        if (toDoList.getToDoListSize() == 0) {
            printNoTasksInList();
        } else {
            System.out.print("Tasks Not Started: " + toDoList.getNumberOfTasksNotStarted() + "\n");
            System.out.print("Tasks In Progress: " + toDoList.getNumberOfTasksInProgress() + "\n");
            System.out.print("Tasks Completed: " + toDoList.getNumberOfTasksCompleted() + "\n");
        }
    }

    // EFFECTS: Adds a task
    private void addTask() {
        System.out.print("Enter Task name: ");
        taskItem = new TaskItem();
        String taskName;
        taskName = input.next();
        taskItem.changeTaskName(taskName);
        System.out.print("Enter description of the task: ");
        String description;
        /* Add code for optional input */
        description = input.next();
        taskItem.changeDescription(description);
        for (TaskItem ti : toDoList.getToDoList()) {
            if (ti.getTaskName().equals(taskItem.getTaskName())) {
                System.out.print(
                        "Note - You have a duplicate task in your list. Deleting or changing the status of one task "
                                + "will affect all with the same name. \n");
                break;
            }
        }
        System.out.print("You have added the task: ");
        toDoList.addTask(taskItem);
        System.out.print(taskItem.getTaskName() + "\n");
    }

    // EFFECTS: Displays ToDoList
    private void displayToDoList() {
        if (toDoList.getToDoListSize() == 0) {
            printNoTasksInList();
        } else {
            int counter = 1;
            for (TaskItem ti : toDoList.getToDoList()) {
                System.out.println(
                        "Task [" + counter + "]: " + ti.getTaskName() + " / Status: " + ti.getStatus()
                                + " / Description: " + ti.getDescription());
                counter++;
            }
        }
    }

    // EFFECTS: Prints text to indicate input String and TaskItem name must match exactly.
    private boolean displayTextExactMatch() {
        if (toDoList.getToDoListSize() == 0) {
            printNoTasksInList();
            return false;
        } else {

            System.out.print(
                    "Displayed below are the current tasks. Enter the number in front of the task to remove it. \n");
            displayToDoList();
            System.out.print("Otherwise, the task may not be found properly. ");
            System.out.print("Enter the task name: \n");
            taskName = input.next();
            // Added variable String = taskNumber; change taskName to taskNumber
            return true;
        }
    }

    private void printNoTasksInList() {
        System.out.print("There are no tasks in the current list. \n");
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
                case "2":
                    toDoList.taskInProgressInToDo(taskName);
                    break;
                case "3":
                    toDoList.taskCompletedInToDo(taskName);
                    break;
                case "1":
                    toDoList.taskNotStartedToDo(taskName);
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
