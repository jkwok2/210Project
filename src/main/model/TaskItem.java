package model;

import exceptions.EmptyException;
import exceptions.NonStatusException;

public class TaskItem {
    private String taskName;
    private String description;
    private String status;

    public TaskItem() {
        this.taskName = "";
        this.description = "";
        status = "Not Started";
    }

    public TaskItem(String name, String description, String status) {
        taskName = name;
        this.description = description;
        this.status = status;
    }

    // MODIFIES: This
    // EFFECTS: Gets the name of a TaskItem
    public void changeTaskName(String newTaskName) throws EmptyException {
        if (newTaskName.equals("")) {
            throw new EmptyException();
        } else {
            this.taskName = newTaskName;
        }
    }

    // MODIFIES: This
    // EFFECTS: Gets the description of a TaskItem
    public void changeDescription(String taskDescription) {
        this.description = taskDescription;
    }

    // MODIFIES: This
    // EFFECTS: Changes task status
    public void changeTaskStatus(String newStatus) throws NonStatusException {
        if (newStatus.equals("Not Started") || newStatus.equals("In Progress") || newStatus.equals("Completed")) {
            this.status = newStatus;
        } else {
            throw new NonStatusException();
        }
    }

    // EFFECTS: Returns the name of a TaskItem
    public String getTaskName() {
        return taskName;
    }

    // EFFECTS: Returns the description of a TaskItem
    public String getDescription() {
        return description;
    }

    // EFFECTS: Returns the status of a TaskItem
    public String getStatus() {
        return status;
    }
}
