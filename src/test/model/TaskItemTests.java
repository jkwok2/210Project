package model;

import static org.junit.jupiter.api.Assertions.*;

import javafx.concurrent.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskItemTests {

    TaskItem taskItem1;

    @BeforeEach
    void runBefore() {
        taskItem1 = new TaskItem();
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
        taskItem1.addDescription("Sample Description");
        assertEquals("Sample Description", taskItem1.getDescription());
    }

}
