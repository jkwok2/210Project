package persistence;

import static org.junit.jupiter.api.Assertions.*;

import model.TaskItem;
import model.ToDoList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class EditorTests {

    private static final String TODO_FILE = "./data/todo_datatest.json";
    private Editor editor;
    File file;
    ToDoList todoList1;
    TaskItem taskItem1;
    TaskItem taskItem2;
    TaskItem taskItem3;
    TaskItem taskItem4;

    @BeforeEach
    void runBefore() {
        try {
            editor = new Editor(new File(TODO_FILE));
            todoList1 = new ToDoList();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        taskItem1 = new TaskItem();
        taskItem1.changeTaskName("Task 1");
        todoList1.addTask(taskItem1);
        taskItem2 = new TaskItem();
        taskItem2.changeTaskName("Task 2");
        taskItem2.changeDescription("test2");
        todoList1.addTask(taskItem2);
        taskItem3 = new TaskItem();
        taskItem3.changeTaskName("Task 3");
        todoList1.addTask(taskItem3);
        todoList1.taskInProgressInToDo("Task 3");
        taskItem4 = new TaskItem();
        taskItem4.changeTaskName("Task 4");
        taskItem4.changeTaskStatusToCompleted();
        todoList1.addTask(taskItem4);
    }

    @Test
    public void testSaveData() {
        JSONObject obj = new JSONObject();
        JSONArray toDoListTaskItemName = new JSONArray();
        JSONArray toDoListTaskItemDescription = new JSONArray();
        JSONArray toDoListTaskItemStatus = new JSONArray();
        for (TaskItem ti : todoList1.getToDoList()) {
            toDoListTaskItemName.add(ti.getTaskName());
            toDoListTaskItemDescription.add(ti.getTaskName());
            toDoListTaskItemStatus.add(ti.getStatus());
        }
        obj.put("Task Item Names", toDoListTaskItemName);
        obj.put("Task Item Descriptions", toDoListTaskItemDescription);
        obj.put("Task Item Statuses", toDoListTaskItemStatus);
        obj.put("numTasks", todoList1.getToDoListSize());
        obj.put("numCompleted", todoList1.getNumberOfTasksCompleted());
        obj.put("numInProgress", todoList1.getNumberOfTasksInProgress());
        obj.put("numNotStarted", todoList1.getNumberOfTasksNotStarted());

        editor.write(obj.toJSONString());
        editor.close();
        assertEquals(1,obj.get("numInProgress"));
        assertEquals(1, obj.get("numCompleted"));
        assertEquals(2, obj.get("numNotStarted"));
        assertEquals(4, obj.get("numTasks"));
//        assertEquals(["Task 1","test2","Task 3","Task 4"],obj.get("Task Item Names"));
    }

    @Test
    public void testUnpackData() {

    }
}
