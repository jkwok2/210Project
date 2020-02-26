package persistence;

import model.TaskItem;
import model.ToDoList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


// This class represents the savable element of the todolist. It contains functions for saving the data by writing to
// JSON and reading it from JSON
public class Editor {

    private PrintWriter printWriter;

    // EFFECTS: constructs a writer that will write data to file
    public Editor(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: writes to file
    public void write(String toDoList) {
        printWriter.write(toDoList);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() {
        printWriter.close();
    }

    //
    public void saveData(ToDoList toDoList) {
        JSONObject obj = new JSONObject();
        JSONArray toDoListTaskItemName = new JSONArray();
        JSONArray toDoListTaskItemDescription = new JSONArray();
        JSONArray toDoListTaskItemStatus = new JSONArray();

        for (TaskItem ti : toDoList.getToDoList()) {
            toDoListTaskItemName.add(ti.getTaskName());
            toDoListTaskItemDescription.add(ti.getDescription());
            toDoListTaskItemStatus.add(ti.getStatus());
        }
        obj.put("Task Item Names", toDoListTaskItemName);
        obj.put("Task Item Descriptions", toDoListTaskItemDescription);
        obj.put("Task Item Statuses", toDoListTaskItemStatus);
        obj.put("numTasks", toDoList.getToDoListSize());
        obj.put("numCompleted", toDoList.getNumberOfTasksCompleted());
        obj.put("numInProgress", toDoList.getNumberOfTasksInProgress());
        obj.put("numNotStarted", toDoList.getNumberOfTasksNotStarted());

        write(obj.toJSONString());
        close();
    }

    //
    public static JSONObject unpackData(FileReader reader) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }
}
