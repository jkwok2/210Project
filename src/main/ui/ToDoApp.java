package ui;

import model.TaskItem;
import model.ToDoList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ToDoApp {
    //fields
    private Scanner input;
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private ToDoList toDoList;
    private TaskItem taskItem;
    private String taskName;
    private String newStatus;

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
//                TODO
            case "6":
                this.changeNameOrDescriptionInit();
                break;
//            case "7":
//            change priority, length of tasks
//            case "8":
//            instruction manual
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    private void changeNameOrDescriptionInit() {
        if (toDoList.getToDoListSize() == 0) {
            noTasksInList();
        } else {
            System.out.print("Enter '1' to change task name or '2' to change task description\n");
            String changeTaskInput = input.next();
            if (changeTaskInput.equals("1")) {
                changeTaskName();
            } else if (changeTaskInput.equals("2")) {
                changeTaskDescription();
            } else {
                System.out.print("Not a valid selection. Please try again\n");
            }
        }
    }

    private void changeTaskName() {
//        TODO: potentially combine the match string code (which the remove and change taskStatus) also uses
        System.out.print("");
        // Prompt for input - what is the name of the task you want to change?
        // Change task method
        // if there are duplicates - ask if you want to change the first, second or third task...
        // Your task name has been changed!
    }

    private void changeTaskDescription() {
//        TODO
        System.out.print("campiest");
        // Prompt for input - what is the description of the task you want to change?
        // Change task method
        // if there are duplicates - ask if you want to change the first, second or third task...
        // Your task name has been changed!
    }

    private void displayStatusStats() {
        if (toDoList.getToDoListSize() == 0) {
            noTasksInList();
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
        String taskName = input.next();
        taskItem.changeTaskName(taskName);
        System.out.print("Enter description of the task: ");;
        /* Add code for optional input */
        String description = input.next();
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
            noTasksInList();
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
            noTasksInList();
            return false;
        } else {

            System.out.print(
                    "Displayed below are the current tasks. Enter the task name to remove it. \n");
            displayToDoList();
            System.out.print(
                    "Your entry must match the task name exactly; any typo may result in an error. ");
            System.out.print("Enter the task name: \n");
            taskName = input.next();
            return true;
        }
    }

    private void noTasksInList() {
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
        System.out.println("- '6' to change a task name or description");
        System.out.println("- 'q' to quit");
    }
}
