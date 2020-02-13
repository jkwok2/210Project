package model;

public class TaskItem {
    private String taskName;
    private String description;
    private String status;

    public TaskItem() {
        this.taskName = "";
        this.description = "";
        status = "Not Started";
    }

    public void addTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void addDescription(String taskDescription) {
        this.description = taskDescription;
    }

    // MODIFIES: this
    // EFFECTS: Sets status to InProgress
    public void changeTaskStatusToInProgress() {
        this.status = "In Progress";
    }

    // MODIFIES: this
    // EFFECTS: Sets status to Completed
    public void changeTaskStatusToCompleted() {
        this.status = "Completed";
    }

    // MODIFIES: this
    // EFFECTS: Sets status to NotStarted
    public void changeTaskStatusToNotStarted() {
        this.status = "Not Started";
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
