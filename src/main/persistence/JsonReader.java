package persistence;

import model.TaskItem;
import model.ToDoList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;


// This class represents the savable element of the todolist. It contains functions for saving the data by writing to
// JSON and reading it from JSON
public class JsonReader {
    private FileReader reader;

    // EFFECTS: constructs a writer that will write data to file
    public JsonReader(File file) throws FileNotFoundException, UnsupportedEncodingException {
        reader = new FileReader(file);
    }

    // EFFECTS: constructs a parser that will write data to file
    public ToDoList unpackData() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        ArrayList<TaskItem> listOfTaskItems = new ArrayList<>();
        ArrayList<String> taskItemNames = (ArrayList<String>) jsonObject.get("Task Item Names");
        ArrayList<String> taskItemDescription = (ArrayList<String>) jsonObject.get("Task Item Descriptions");
        ArrayList<String> taskItemStatus = (ArrayList<String>) jsonObject.get("Task Item Statuses");
        long numTasks = (Long) jsonObject.get("numTasks");
        long numCompleted = (Long) jsonObject.get("numCompleted");
        long numInProgress = (Long) jsonObject.get("numInProgress");
        long numNotStarted = (Long) jsonObject.get("numNotStarted");
        addParam(listOfTaskItems, taskItemNames, taskItemDescription, taskItemStatus);
        return new ToDoList(listOfTaskItems, numTasks, numNotStarted, numCompleted, numInProgress);
    }

    private void addParam(
            ArrayList<TaskItem> lti, ArrayList<String> tin, ArrayList<String> tid, ArrayList<String> tis) {
        for (int i = 0; i < tin.size(); i++) {
            String taskName = tin.get(i);
            String taskDescription = tid.get(i);
            String taskStatus = tis.get(i);
            lti.add(new TaskItem(taskName, taskDescription, taskStatus));
        }
    }
}
