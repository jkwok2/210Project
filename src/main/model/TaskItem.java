package model;

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

    // TODO: Create Exception if you are charging a non-empty field to an empty one
    // EFFECTS: Gets the name of a TaskItem
    public void changeTaskName(String taskName) {
        this.taskName = taskName;
    }

    // EFFECTS: Gets the description of a TaskItem
    public void changeDescription(String taskDescription) {
        this.description = taskDescription;
    }

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
