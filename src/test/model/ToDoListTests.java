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
    public void testTaskChanges() {
        todoList2.taskCompletedInToDo(taskItem3.getTaskName());
//        Task 1, 2, 4 - Not Started; Task 3 - Completed
        assertEquals("Completed", taskItem3.getStatus());
        assertEquals(3, todoList2.numNotStarted);
        assertEquals(0, todoList2.numInProgress);
        assertEquals(1, todoList2.numCompleted);
        todoList2.taskInProgressInToDo(taskItem4.getTaskName());
//        Task 1, 2, - Not Started; Task 3 - Completed; Task 4 - In Progress
        assertEquals(1, todoList2.numCompleted);
        assertEquals(1, todoList2.numInProgress);
        assertEquals(2, todoList2.numNotStarted);
        todoList2.taskInProgressInToDo(taskItem2.getTaskName());
//        Task 1 - Not Started; Task 3 - Completed; Task 2, 4 - In Progress
        todoList2.taskCompletedInToDo(taskItem4.getTaskName());
//        Task 1 - Not Started; Task 3, 4 - Completed; Task 2 - In Progress
        assertEquals(2, todoList2.numCompleted);
        assertEquals(1, todoList2.numInProgress);
        assertEquals(1, todoList2.numNotStarted);
        todoList2.taskNotStartedToDo(taskItem2.getTaskName());
//        Task 1, 2 - Not Started; Task 3, 4 - Completed
        assertEquals(2, todoList2.numNotStarted);
        assertEquals(0, todoList2.numInProgress);
        assertEquals(2, todoList2.numCompleted);
        todoList2.taskNotStartedToDo(taskItem3.getTaskName());
//        Task 1, 2, 3 - Not Started; Task 4 - Completed
        assertEquals(3, todoList2.numNotStarted);
        assertEquals(0, todoList2.numInProgress);
        assertEquals(1, todoList2.numCompleted);
        todoList2.taskInProgressInToDo(taskItem4.getTaskName());
//        Task 1, 2, 3 - Not Started; Task 4 - In Progress
        assertEquals(3, todoList2.numNotStarted);
        assertEquals(1, todoList2.numInProgress);
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
        assertEquals(4, todoList2.getNumberOfTasksNotStarted());
        todoList2.taskInProgressInToDo("Task 3");
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