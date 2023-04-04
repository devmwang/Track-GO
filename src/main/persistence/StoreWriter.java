// Code adapted from CPSC 210 JSON Serialization Demo at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java

package persistence;

import java.io.*;

import model.Event;
import model.EventLog;
import org.json.JSONObject;

import model.AppData;

// Represents a writer that writes app data to a JSON file
public class StoreWriter {
    private static final int INDENT = 4;
    private PrintWriter writer;
    private final String filePath;

    // EFFECTS: Constructs writer to write data to file at filePath
    public StoreWriter(String filePath) {
        this.filePath = filePath;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(filePath);
    }

    // REQUIRES: appData is not null
    // MODIFIES: this
    // EFFECTS: Writes JSON representation of appData to file
    public void write(AppData appData) {
        JSONObject json = appData.toJson();
        saveToFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: Closes writer
    public void close() {
        writer.close();
    }

    // REQUIRES: data is not null
    // MODIFIES: this
    // EFFECTS: Writes data string to file
    private void saveToFile(String data) {
        writer.print(data);
    }
}
