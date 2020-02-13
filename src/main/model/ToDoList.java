package model;

import java.lang.reflect.Array;
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

    public void addTask(TaskItem ti) {
        toDoList.add(ti);
        numNotStarted++;
    }

    // MODIFIES: TaskItem Status
    // EFFECTS: Compares the given string against the name of the TaskItems. If there is a match, the status of the
    //          TaskItem is changed to completed and the completed counter is incremented.
    public void taskCompletedInToDo(String name) {
        for (TaskItem ti : toDoList) {
            if (ti.getTaskName().equals(name)) {
                ti.changeTaskStatusToCompleted();
                this.numCompleted++;
            }
        }
    }

    // MODIFIES: TaskItem Status
    // EFFECTS: Compares the given string against the name of the TaskItems. If there is a match, the status of the
    //          TaskItem is changed to completed and the In Progress counter is incremented.
    public void taskInProgressInToDo(String name) {
        for (TaskItem ti : toDoList) {
            if (ti.getTaskName().equals(name)) {
                ti.changeTaskStatusToInProgress();
                this.numInProgress++;
            }
        }
    }

    // REQUIRES: pos > 0
    // MODIFIES: this
    // EFFECTS: Given pos, remove that specific item in the toDoList
    public ArrayList<TaskItem> removeTask(int pos) {
        TaskItem ti;
        ti = toDoList.get(pos);
        toDoList.remove(ti);
        return toDoList;
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
}
