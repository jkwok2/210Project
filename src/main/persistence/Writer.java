package persistence;

import model.TaskItem;
import model.ToDoList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Writer {

    private PrintWriter printWriter;

    // EFFECTS: constructs a writer that will write data to file
    public Writer(File file) throws FileNotFoundException, UnsupportedEncodingException {
        printWriter = new PrintWriter(file, "UTF-8");
    }

    // MODIFIES: this
    // EFFECTS: writes saveable to file
    public void write(String toDoList) {
        printWriter.write(toDoList);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() {
        printWriter.close();
    }

    public void saveData(ToDoList toDoList) {
        JSONObject obj = new JSONObject();
        JSONArray toDoListTaskItemName = new JSONArray();
        for (TaskItem ti : toDoList.getToDoList()) {
            toDoListTaskItemName.add(ti.getTaskName());
        }
        obj.put("Task Item Names", toDoListTaskItemName);
        JSONArray toDoListTaskItemDescription = new JSONArray();
        for (TaskItem ti : toDoList.getToDoList()) {
            toDoListTaskItemDescription.add(ti.getTaskName());
        }
        obj.put("Task Item Names", toDoListTaskItemDescription);


        obj.put("num", new Integer(100));
        obj.put("balance", new Double(1000.21));
        obj.put("is_vip", new Boolean(true));

        write(obj.toJSONString());
        close();
    }
}
