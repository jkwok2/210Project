package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ToDoListTests {

    ToDoList todoList1;
    ToDoList todoList2;
    ToDoList todoList3;
    TaskItem taskItem1;
    TaskItem taskItem2;
    TaskItem taskItem3;
    TaskItem taskItem4;
    ToDoList todoList4;

    @BeforeEach
    void runBefore() {
        todoList4 = new ToDoList(new ArrayList<>(), (long) 0, (long) 0, (long) 0, (long) 0);
        todoList1 = new ToDoList();
        taskItem1 = new TaskItem();
        taskItem1.changeTaskName("Task 1");
        taskItem2 = new TaskItem();
        taskItem2.changeTaskName("Task 2");
        taskItem3 = new TaskItem();
        taskItem3.changeTaskName("Task 3");
        taskItem4 = new TaskItem();
        taskItem4.changeTaskName("Task 4");
        todoList2 = new ToDoList();
        todoList2.addTask(taskItem1);
        todoList2.addTask(taskItem2);
        todoList2.addTask(taskItem3);
        todoList2.addTask(taskItem4);
        todoList3 = new ToDoList();
        todoList3.addTask(taskItem1);
        todoList3.addTask(taskItem2);
        todoList3.addTask(taskItem4);
    }

    @Test
    void testRemoveTaskItemNotStarted() {
        todoList2.removeTask(2);
        assertEquals(3, todoList2.toDoList.size());
        taskItem1.changeTaskStatus("In Progress");
        todoList2.addNumInProgress();
        todoList2.subNumNotStarted();
        todoList2.removeTask(2);
    }

    @Test
    void testRemoveTaskItemInProgress() {
        taskItem1.changeTaskStatus("In Progress");
        todoList2.addNumInProgress();
        todoList2.subNumNotStarted();
        todoList2.removeTask(0);
        assertEquals(3, todoList2.toDoList.size());
    }

    @Test
    void testRemoveTaskItemCompleted() {
        taskItem1.changeTaskStatus("Completed");
        todoList2.addNumInProgress();
        todoList2.subNumNotStarted();
        todoList2.removeTask(0);
        assertEquals(3, todoList2.toDoList.size());
    }

    @Test
    void testAddTaskItem() {
        todoList1.addTask(taskItem1);
        assertEquals(1, todoList1.toDoList.size());
        assertEquals(1, todoList1.numNotStarted);
        assertEquals("[Task 1]", todoList1.convertToDoListToString().toString());
    }

    @Test
    void testConvertToDoListToString() {
        todoList2.convertToDoListToString();
    }

    @Test
    public void testTaskInProgress() {
        taskItem1.changeTaskStatus("In Progress");
        todoList2.addNumInProgress();
        todoList2.subNumNotStarted();
        assertEquals("In Progress", taskItem1.getStatus());
        assertEquals(1, todoList2.numInProgress);
    }


    @Test
    void testMatchTaskItem() {
        assertEquals(1, todoList2.taskPosition("Task 2"));
        assertEquals(-1, todoList2.taskPosition("random name"));
    }

    @Test
    public void testGetNumberOfTasksStarted() {
        assertEquals(4, todoList2.getNumberOfTasksNotStarted());
        taskItem3.changeTaskStatus("In Progress");
        todoList2.addNumInProgress();
        todoList2.subNumNotStarted();
        assertEquals(3, todoList2.getNumberOfTasksNotStarted());
        assertEquals(1, todoList2.getNumberOfTasksInProgress());
        assertEquals(0, todoList2.getNumberOfTasksCompleted());
    }

    @Test
    public void testGetToDoListSize() {
        assertEquals(0, todoList1.getToDoListSize());
        todoList1.addTask(taskItem1);
        assertEquals(1, todoList1.getToDoListSize());
        todoList1.removeTask(0);
        assertEquals(0, todoList1.getToDoListSize());
    }

    @Test
    public void testGetTaskItem() {
        assertEquals(taskItem1, todoList2.getTaskItem(0));
    }

    @Test
    public void testChangeTaskStatus() {
        assertEquals("Not Started", todoList2.getTaskItem(0).getStatus());
        todoList2.getTaskItem(0).changeTaskStatusToInProgress();
        assertEquals("In Progress", todoList2.getTaskItem(0).getStatus());
        todoList2.getTaskItem(1).changeTaskStatusToCompleted();
        assertEquals("Completed", todoList2.getTaskItem(1).getStatus());
    }

    @Test
    public void testNumTaskStatus() {
        todoList2.addNumNotStarted();
        todoList2.addNumInProgress();
        todoList2.addNumCompleted();
        assertEquals(5, todoList2.getNumberOfTasksNotStarted());
        assertEquals(1, todoList2.getNumberOfTasksInProgress());
        assertEquals(1, todoList2.getNumberOfTasksCompleted());
        todoList2.subNumNotStarted();
        todoList2.subNumInProgress();
        todoList2.subNumCompleted();
        assertEquals(4, todoList2.getNumberOfTasksNotStarted());
        assertEquals(0, todoList2.getNumberOfTasksInProgress());
        assertEquals(0, todoList2.getNumberOfTasksCompleted());
    }

}