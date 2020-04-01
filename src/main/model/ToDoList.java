package model;

import java.util.ArrayList;

public class ToDoList {
    ArrayList<TaskItem> toDoList;
    int numTasks;
    int numCompleted;
    int numInProgress;
    int numNotStarted;

    public ToDoList() {
        toDoList = new ArrayList<>();
        numTasks = 0;
        numNotStarted = 0;
        numCompleted = 0;
        numInProgress = 0;
    }

    public ToDoList(
            ArrayList<TaskItem> toDoList, Long numTasks, Long numNotStarted, Long numCompleted, Long numInProgress) {
        this.toDoList = toDoList;
        this.numTasks = numTasks.intValue();
        this.numNotStarted = numNotStarted.intValue();
        this.numCompleted = numCompleted.intValue();
        this.numInProgress = numInProgress.intValue();
    }

    // EFFECTS: Adds task and increments counter
    public void addTask(TaskItem ti) {
        toDoList.add(ti);
        numNotStarted++;
    }

    // REQUIRES: pos > 0
    // MODIFIES: this
    // EFFECTS: Given pos, remove that specific item in the toDoList
    public void removeTask(int pos) {
        TaskItem ti;
        ti = toDoList.get(pos);
        if (ti.getStatus().equals("Not Started")) {
            numNotStarted--;
        } else if (ti.getStatus().equals("In Progress")) {
            numInProgress--;
        } else {
            numCompleted--;
        }
        toDoList.remove(ti);
    }

    public ArrayList<TaskItem> getToDoList() {
        return toDoList;
    }

    // EFFECTS: Converts a List to String
    public ArrayList<String> convertToDoListToString() {
        ArrayList<TaskItem> a1 = getToDoList();
        ArrayList<String> output = new ArrayList<>();
        for (TaskItem i : a1) {
            output.add(i.getTaskName());
        }
        return output;
    }

    // EFFECTS: Check whether a given string matches the name of any TaskItem in the TodoList
    public int taskPosition(String givenTaskName) {
        int counter = 0;
        for (TaskItem ti : toDoList) {
            counter++;
            if (givenTaskName.equals(ti.getTaskName())) {
                return counter - 1;
            }
        }
        return -1;
    }

    public void subNumNotStarted() {
        this.numNotStarted--;
    }

    public void addNumNotStarted() {
        this.numNotStarted++;
    }

    public void subNumInProgress() {
        this.numInProgress--;
    }

    public void addNumInProgress() {
        this.numInProgress++;
    }

    public void subNumCompleted() {
        this.numCompleted--;
    }

    public void addNumCompleted() {
        this.numCompleted++;
    }

    public int getNumberOfTasksNotStarted() {
        return numNotStarted;
    }

    public int getNumberOfTasksInProgress() {
        return numInProgress;
    }

    public int getNumberOfTasksCompleted() {
        return numCompleted;
    }

    public int getToDoListSize() {
        return toDoList.size();
    }

    public TaskItem getTaskItem(int itemNumber) {
        return toDoList.get(itemNumber);
    }
}
