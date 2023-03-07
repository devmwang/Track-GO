package persistence;

import org.json.JSONObject;

public interface Readable {
    // EFFECTS: loads data from json object
    void fromJson(JSONObject jsonObject);
}