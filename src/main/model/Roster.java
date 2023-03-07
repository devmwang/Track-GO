package model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;
import persistence.Readable;

// Represents a roster of players to be tracked by the application
public class Roster implements Writable, Readable {
    private final String id;
    private final ArrayList<Player> players;
    private int gamesPlayed;
    private int roundsPlayed;
    private int wins;
    private int losses;

    // EFFECTS: Constructs new Roster with given id and players, and other fields set to default value 0
    public Roster(String id, ArrayList<Player> players) {
        this.id = id;
        this.players = players;
        this.gamesPlayed = 0;
        this.roundsPlayed = 0;
        this.wins = 0;
        this.losses = 0;
    }

    // REQUIRES: player is not null
    // MODIFIES: this
    // EFFECTS: Adds player to roster
    public void addPlayer(Player player) {
        players.add(player);
    }

    // REQUIRES: player is not null
    // MODIFIES: this
    // EFFECTS: Removes player from roster
    public void removePlayer(Player player) {
        players.remove(player);
    }

    // MODIFIES: this
    // EFFECTS: Increments gamesPlayed by 1
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    // REQUIRES: rounds >= 0
    // MODIFIES: this
    // EFFECTS: Increments roundsPlayed by rounds
    public void incrementRoundsPlayed(int rounds) {
        this.roundsPlayed += rounds;
    }

    // MODIFIES: this
    // EFFECTS: Increments wins by 1
    public void incrementWins() {
        this.wins++;
    }

    // MODIFIES: this
    // EFFECTS: Increments losses by 1
    public void incrementLosses() {
        this.losses++;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // EFFECTS: Returns win rate percentage value to 1 decimal point
    public double getWinRate() {
        if (gamesPlayed == 0) {
            return 0;
        }
        return Math.floor((double) wins / (double) gamesPlayed * 1000) / 10;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return gamesPlayed - wins - losses;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("id", id);

        JSONArray jsonArray = new JSONArray();
        for (Player player : players) {
            jsonArray.put(player.getUsername());
        }
        json.put("players", jsonArray);

        json.put("gamesPlayed", gamesPlayed);
        json.put("roundsPlayed", roundsPlayed);
        json.put("wins", wins);
        json.put("losses", losses);

        return json;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        this.gamesPlayed = jsonObject.getInt("gamesPlayed");
        this.roundsPlayed = jsonObject.getInt("roundsPlayed");
        this.wins = jsonObject.getInt("wins");
        this.losses = jsonObject.getInt("losses");
    }
}
