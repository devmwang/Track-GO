package model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a match to be tracked by the application
public class Match implements Writable {
    private final int matchId;
    private final ArrayList<Player> players;
    private final int roundsWon;
    private final int roundsLost;
    private final String map;

    // EFFECTS: Constructs a match with the provided roster and data values
    public Match(int matchId, Roster roster, int roundsWon, int roundsLost, String map) {
        this.matchId = matchId;
        this.players = new ArrayList<>();
        this.players.addAll(roster.getPlayers());
        this.roundsWon = roundsWon;
        this.roundsLost = roundsLost;
        this.map = map;
    }

    // EFFECTS: Constructs a match with the provided player list and data values
    public Match(int matchId, ArrayList<Player> players, int roundsWon, int roundsLost, String map) {
        this.matchId = matchId;
        this.players = players;
        this.roundsWon = roundsWon;
        this.roundsLost = roundsLost;
        this.map = map;
    }

    public int getMatchId() {
        return matchId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // EFFECTS: Returns the total number of rounds played in this match (wonRounds + lostRounds)
    public int getTotalRounds() {
        return roundsWon + roundsLost;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public int getRoundsLost() {
        return roundsLost;
    }

    public String getMap() {
        return map;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("matchId", matchId);

        JSONArray jsonArray = new JSONArray();
        for (Player player : players) {
            jsonArray.put(player.getUsername());
        }
        json.put("players", jsonArray);

        json.put("roundsWon", roundsWon);
        json.put("roundsLost", roundsLost);
        json.put("map", map);

        return json;
    }
}
