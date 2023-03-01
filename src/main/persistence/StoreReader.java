// Code adapted from CPSC 210 JSON Serialization Demo at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java

package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;
import org.json.*;

import model.*;
import exceptions.*;

// Represents a reader that reads app data from a JSON file
public class StoreReader {
    private final String filePath;

    // EFFECTS: Constructs reader to read from source file
    public StoreReader(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: Reads players data from file and applies it to list, throws IOException if error occurs while loading
    public void loadPlayers(ArrayList<Player> players) throws IOException {
        String jsonData = readFile(filePath);
        JSONObject jsonObject = new JSONObject(jsonData);

        parsePlayers(players, jsonObject);
    }

    // EFFECTS: Reads source file as a string and returns it
    private String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(string -> contentBuilder.append(string));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: appData
    // EFFECTS: Parses players from JSON object and adds them to appData
    private void parsePlayers(ArrayList<Player> players, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");

        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;

            Player player = new Player(nextPlayer.getString("username"));

            players.add(player);
        }
    }

    // MODIFIES: appData
    // EFFECTS: Parses rosters from JSON object and adds them to appData
    private void parseRosters(AppData appData, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rosters");

        for (Object json : jsonArray) {
            JSONObject nextRoster = (JSONObject) json;

            ArrayList<Player> players = getPlayers(appData, nextRoster);

            Roster roster = new Roster(nextRoster.getString("id"), players);
        }
    }

    // EFFECTS: Returns a list of players based on consumed roster data in JSON object
    private ArrayList<Player> getPlayers(AppData appData, JSONObject jsonObject) {
        ArrayList<String> playerUsernames = new ArrayList<>();

        for (Object obj : jsonObject.getJSONArray("rosterPlayers")) {
            playerUsernames.add((String) obj);
        }

        ArrayList<Player> players = new ArrayList<>();

        for (String username : playerUsernames) {
            try {
                players.add(appData.getPlayerByUsername(username));
            } catch (PlayerNotFoundException e) {
                System.out.println("Player not found");
            }
        }

        return players;
    }

    // MODIFIES: appData
    // EFFECTS: Parses matches from JSON object and adds them to appData
    private void parseMatches(AppData appData, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("matches");
    }
}
