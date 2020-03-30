package model;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testChangeTaskStatus() {
        taskItem1.changeTaskStatus("In Progress");
        assertEquals("In Progress", taskItem1.getStatus());
    }

    @Test
    public void testNotStarted() {
        taskItem1.changeTaskStatusToNotStarted();
        assertEquals("Not Started", taskItem1.getStatus());
    }

    @Test
    public void testChangeCompleted() {
        taskItem1.changeTaskStatusToCompleted();
        assertEquals("Completed", taskItem1.getStatus());
    }

    @Test
    public void testGetDescription() {
        taskItem1.changeDescription("Sample Description");
        assertEquals("Sample Description", taskItem1.getDescription());
    }

}
