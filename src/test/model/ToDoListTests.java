package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToDoListTests {
    // delete or rename this class!

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

//    @Test
//    void testRemoveTaskItem() {
//        todoList2.removeTask(2);
//
////        for (TaskItem taskItem : todoList2.toDoList) {
////            assertEquals();
//        }
//    }

    @Test
    void testAddTask() {
        todoList1.addTask(taskItem1);
        assertEquals(1, todoList1.toDoList.size());
        assertEquals(1, todoList1.numNotStarted);
    }

//    @Test
//    public void testChangeInProgress() {
//        todoList2.changeInProgress(taskItem3);
//        assertEquals("In Progress", taskItem3.getStatus());
//        assertEquals(1, );
//    }

    @Test
    public void testNotStarted() {
        taskItem1.changeNotStarted();
        assertEquals("Not Started", taskItem1.getStatus());
    }

    @Test
    public void testChangeCompleted() {
        taskItem1.changeCompleted();
        assertEquals("Completed", taskItem1.getStatus());
    }

    @Test
    void testMatchTaskItem() {
        assertEquals(1, todoList2.taskPosition("Task 2"));
        assertEquals(-1, todoList2.taskPosition("random name"));
    }

    @Test
    public void testGetDescription() {
        taskItem1.addDescription("Sample Description");
        assertEquals("Sample Description", taskItem1.getDescription());
    }
}