package ui;

import model.TaskItem;
import model.ToDoList;

import java.util.Scanner;

public class ToDoApp {
    //fields
    private Scanner input;
    private ToDoList toDoList;
    private TaskItem taskItem;
    private String taskName;
    private String newStatus;

    // EFFECTS: runs the teller application
    public ToDoApp() {
        runList();
        newStatus = "";
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runList() {
        boolean runStatus = true;
        String command;
        init();
        while (runStatus) {
            displayMenu();
            command = input.nextLine();
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
                this.changeNameOrDescription();
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

    private void changeNameOrDescription() {
        if (toDoList.getToDoListSize() == 0) {
            noTasksInList();
        } else {
            System.out.print("Enter '1' to change task name or '2' to change task description\n");
            String numInput = input.next();
            switch (numInput) {
                case "1":
                    changeTaskName();
                    break;
                case "2":
                    changeTaskDescription();
                    break;
                default:
                    System.out.print("Not a Valid Selection\n");
                    break;
            }
            // TODO: The above breaks things: Exception in thread "main" java.lang.NullPointerException
//            if (changeTaskInput.equals("1")) {
//                changeTaskName();
//            } else if (changeTaskInput.equals("2")) {
//                changeTaskDescription();
//            } else {
//                System.out.print("Not a valid selection. Please try again\n");
//            }
        }
    }

    private void changeTaskName() {
//        TODO: potentially combine the match string code (which the remove and change taskStatus) also uses
        if (displayTextExactMatch()) {
            Scanner input2 = new Scanner(System.in);
            String newTaskName = input2.nextLine();
            int pos = toDoList.taskPosition(newTaskName);
            if (pos == -1) {
                System.out.print("No Matching Task. Returning to the Welcome Screen... \n");
            } else {
                toDoList.getTaskItem(pos - 1).changeTaskName(newTaskName);
                System.out.print("Task Name Changed. Returning to the Welcome Screen... \n");
            }
        }
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

    // MODIFIES: taskName
    // EFFECTS: Generates new taskName combined with spaces
    private String appendTaskName() {
        taskItem = new TaskItem();
        String firstWordOfTaskName = input.next();
        String restOfTaskName = input.nextLine();
        String taskName = firstWordOfTaskName + restOfTaskName;
        return taskName;
    }

    private String appendDescription() {
        String firstWordOfDescription = input.next();
        String restOfDescription = input.nextLine();
        String description = firstWordOfDescription + restOfDescription;
        return description;
    }

    // EFFECTS: Adds a task
    private void addTask() {
        System.out.print("Enter Task name: ");
        String taskName = appendTaskName();
        taskItem.changeTaskName(taskName);
        System.out.print("Enter description of the task: ");
        String description = appendDescription();
        taskItem.changeDescription(description);
        /* Add code for optional input */
        for (TaskItem ti : toDoList.getToDoList()) {
            if (ti.getTaskName().equals(taskItem.getTaskName())) {
                System.out.print(
                        "Note - You have a duplicate task in your list. Deleting or changing the status of one task "
                                + "will affect all tasks with the same name. \n");
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
                    "Displayed below are the current tasks: \n");
            displayToDoList();
            System.out.print("Enter the name of the task you wish to modify: ");
            taskName = appendTaskName();
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
                return;
            } else {
                System.out.print("Enter '1' for Not Started, '2' for In Progress, and '3' for Completed. \n");
                newStatus = input.next();
            }
            printTaskStatus();
        }
    }

    private void printTaskStatus() {
        switch (newStatus) {
            case "2":
                toDoList.taskInProgressInToDo(taskName);
                System.out.print("Task Status Modified. \n");
                break;
            case "3":
                toDoList.taskCompletedInToDo(taskName);
                System.out.print("Task Status Modified. \n");
                break;
            case "1":
                toDoList.taskNotStartedToDo(taskName);
                System.out.print("Task Status Modified. \n");
                break;
            default:
                System.out.print("Not a Valid Status. Returning to home menu...\n");
                break;
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
