package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToDoListTests {

    ToDoList todoList1;
    ToDoList todoList2;
    ToDoList todoList3;
    TaskItem taskItem1;
    TaskItem taskItem2;
    TaskItem taskItem3;
    TaskItem taskItem4;

    @BeforeEach
    void runBefore() {
        todoList1 = new ToDoList();
        taskItem1 = new TaskItem();
        taskItem1.addTaskName("Task 1");
        taskItem2 = new TaskItem();
        taskItem2.addTaskName("Task 2");
        taskItem3 = new TaskItem();
        taskItem3.addTaskName("Task 3");
        taskItem4 = new TaskItem();
        taskItem4.addTaskName("Task 4");
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
    void testRemoveTaskItem() {
        todoList2.removeTask(2);
        for (TaskItem taskItem : todoList2.toDoList) {
            assertEquals(3, todoList2.toDoList.size());
        }
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
    public void testTaskCompletedInToDo() {
        todoList2.taskCompletedInToDo(taskItem3.getTaskName());
        assertEquals("Completed", taskItem3.getStatus());
        assertEquals(1, todoList2.numCompleted);
        todoList2.taskInProgressInToDo(taskItem3.getTaskName());
        assertEquals(0, todoList2.numCompleted);
    }

    @Test
    public void testTaskInProgress() {
        todoList2.taskInProgressInToDo(taskItem1.getTaskName());
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
        assertEquals(4,todoList2.getNumberOfTasksNotStarted());
        todoList2.taskInProgressInToDo("Task 3");
        assertEquals(3,todoList2.getNumberOfTasksNotStarted());
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
}