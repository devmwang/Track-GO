// Code adapted from CPSC 210 JSON Serialization Demo at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java

package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

import model.*;
import exceptions.AppDataInvalidException;

// Represents a reader that reads app data from a JSON file
public class StoreReader {
    private final String filePath;

    // EFFECTS: Constructs reader to read from source file
    public StoreReader(String filePath) {
        this.filePath = filePath;
    }

    // REQUIRES: appData is not null
    // MODIFIES: appData
    // EFFECTS: Reads app data from file and returns it, throws IOException if an error occurs while reading data
    public void read(AppData appData) throws IOException, AppDataInvalidException {
        String jsonData = readFile(filePath);
        JSONObject jsonObject = new JSONObject(jsonData);

        appData.fromJson(jsonObject);

        EventLog.getInstance().logEvent(new Event("App data loaded from file at \"" + filePath + "\""));
    }

    // REQUIRES: filePath is not null
    // EFFECTS: Reads source file as a string and returns it
    private String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }
}
