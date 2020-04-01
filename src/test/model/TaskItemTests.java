package model;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.EmptyException;
import exceptions.NonStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskItemTests {

    TaskItem taskItem1;
    TaskItem taskItem2;

    @BeforeEach
    void runBefore() {
        taskItem1 = new TaskItem();
        taskItem2 = new TaskItem("Test Name", "Test Description", "Not Completed");
    }

    @Test
    public void testChangeTaskName() {
        try {
            taskItem1.changeTaskName("Sample Task Name 2");
            assertEquals("Sample Task Name 2", taskItem1.getTaskName());
        } catch (EmptyException e) {
            fail("Was not expecting an exception.");
        }
    }

    @Test
    public void testChangeTaskNameToEmpty() {
        try {
            taskItem1.changeTaskName("");
            fail("Should not reach this point.");
        } catch (EmptyException e) {
            System.out.println("Task name cannot be empty.");
        }
    }

    @Test
    public void testChangeTaskStatusNotValid() {
        try {
            taskItem1.changeTaskStatus("Invalid Status");
            fail("Should not reach this point.");
        } catch (NonStatusException e) {
            System.out.println("This is not a Valid Status.");
        }
    }

    @Test
    public void testChangeTaskStatus() {
        try {
            taskItem1.changeTaskStatus("In Progress");
            assertEquals("In Progress", taskItem1.getStatus());
        } catch (NonStatusException e) {
            fail("Was not expecting an exception.");
        }
    }

    @Test
    public void testGetDescription() {
        taskItem1.changeDescription("Sample Description");
        assertEquals("Sample Description", taskItem1.getDescription());
    }

}
