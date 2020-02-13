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

    // remove that specific item in the toDoList
    public ArrayList<TaskItem> removeTask(int pos) {
        TaskItem ti;
        ti = toDoList.get(pos);
        toDoList.remove(ti);
        return toDoList;
    }

    public ArrayList<TaskItem> getToDoList() {
        return this.toDoList;
    }

    // Check whether a given string matches the name of any TaskItem in the TodoList
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
