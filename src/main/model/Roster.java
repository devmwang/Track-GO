package model;

import java.util.ArrayList;

// Represents a roster of players to be tracked by the application
public class Roster {
    private String id;
    private ArrayList<Player> players;
    private int gamesPlayed;
    private int roundsPlayed;
    private int wins;
    private int ties;

    public Roster(String id) {
        this.id = id;
        this.players = new ArrayList<Player>();
        this.gamesPlayed = 0;
        this.roundsPlayed = 0;
        this.wins = 0;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public String getOverview() {
        return "";
    }

    public String getId() {
        return id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
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

    public int getTies() {
        return ties;
    }

    public int getLosses() {
        return gamesPlayed - wins - ties;
    }
}
