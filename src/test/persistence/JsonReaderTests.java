package persistence;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.NonStatusException;
import model.TaskItem;
import model.ToDoList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class JsonReaderTests {

    private static final String TODO_FILE = "./data/todo_datatest.json";
    FileReader reader;
    JsonReader jsonReader;
    JSONParser parser;
    JSONObject jsonObject;
    ToDoList todoList1;
    TaskItem taskItem1;
    TaskItem taskItem2;
    TaskItem taskItem3;
    TaskItem taskItem4;

    @BeforeEach
    void runBefore() throws FileNotFoundException, NonStatusException {
        todoList1 = new ToDoList();
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
        taskItem3.changeTaskStatus("In Progress");
        todoList1.addNumInProgress();
        todoList1.subNumNotStarted();
        taskItem4 = new TaskItem();
        taskItem4.changeTaskName("Task 4");
        todoList1.addTask(taskItem4);
        taskItem4.changeTaskStatus("Completed");
        todoList1.addNumCompleted();
        todoList1.subNumNotStarted();
        jsonReader = new JsonReader(new File(TODO_FILE));
        reader = new FileReader(new File(TODO_FILE));
        parser = new JSONParser();
    }

    @Test
    public void testUnpackData() throws IOException, ParseException {
        jsonObject = (JSONObject) parser.parse(reader);
        try {
            reader = new FileReader(new File(TODO_FILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            todoList1 = jsonReader.unpackData();
            assertEquals(1, (int) (long) jsonObject.get("numInProgress"));
            assertEquals(1, (int) (long) jsonObject.get("numCompleted"));
            assertEquals(2, (int) (long) jsonObject.get("numNotStarted"));
            assertEquals(4, (int) (long) jsonObject.get("numTasks"));
            assertEquals
                    ("[\"Task 1\",\"Task 2\",\"Task 3\",\"Task 4\"]",
                            jsonObject.get("Task Item Names").toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
