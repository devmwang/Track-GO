package model;

import java.util.ArrayList;

public class AppData {
    private ArrayList<Player> players;
    private ArrayList<Roster> rosters;
    private ArrayList<Match> matches;

    public AppData() {
        this.players = new ArrayList<>();
        this.rosters = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    // EFFECTS: Returns list of players
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // EFFECTS: Returns list of rosters
    public ArrayList<Roster> getRosters() {
        return rosters;
    }

    // EFFECTS: Returns list of matches
    public ArrayList<Match> getMatches() {
        return matches;
    }
}
