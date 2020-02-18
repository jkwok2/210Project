package model;

public class TaskItem {
    private String taskName;
    private String description;
    private String status;
    private int priority;
    private int estimatedTime;
    private int actualTime;

    // TODO: Talk to Scott about this when he's sober
    public TaskItem() {
        this.taskName = "";
        this.description = "";
        status = "Not Started";
        priority = 0;
        estimatedTime = 0;
        actualTime = 0;
        // What happens when you do instantiate it here?

    }

    // EFFECTS: Gets the name of a TaskItem
    public void changeTaskName(String taskName) {
        this.taskName = taskName;
    }

    // EFFECTS: Gets the description of a TaskItem
    public void changeDescription(String taskDescription) {
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

    public void changePriority(int newPriority) {
        this.priority = newPriority;
    }

    public void changeEstimateTime(int newEstimatedTime) {
        this.estimatedTime = newEstimatedTime;
    }

    public void changeActualTime(int newActualTime) {
        this.actualTime = newActualTime;
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

    // EFFECTS: Returns the priority of a taskItem
    public int getPriority() {
        return priority;
    }

    // EFFECTS: Returns the estimated time (for completion) of a taskItem
    public int getEstimatedTime() {
        return estimatedTime;
    }

    // EFFECTS: Returns the actual time (for completion) of a taskItem
    public int getActualTime() {
        return actualTime;
    }
}
