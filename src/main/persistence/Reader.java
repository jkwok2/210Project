package persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Reader {

    public static void readData(FileReader reader) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        String itemName = (String) jsonObject.get("Task Item Names");
        String itemDescription = (String) jsonObject.get("Task Item Descriptions");
        String itemStatus = (String) jsonObject.get("Task Item Statuses");
        long numTasks = (Long) jsonObject.get("numTasks");
        long numCompleted = (Long) jsonObject.get("numCompleted");
        long numInProgress = (Long) jsonObject.get("numInProgress");
        long numNotStarted = (Long) jsonObject.get("numNotStarted");
    }


}
