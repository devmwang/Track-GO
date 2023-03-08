package persistence;

import org.json.JSONObject;

import exceptions.AppDataInvalidException;

public interface Readable {
    // EFFECTS: loads data from json object
    void fromJson(JSONObject jsonObject) throws AppDataInvalidException;
}