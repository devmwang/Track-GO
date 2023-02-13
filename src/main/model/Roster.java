package model;

import java.util.ArrayList;

// Represents a roster of players to be tracked by the application
public class Roster {
    private final String id;
    private final ArrayList<Player> players;
    private int gamesPlayed;
    private int roundsPlayed;
    private int wins;
    private int losses;

    public Roster(String id, ArrayList<Player> players) {
        this.id = id;
        this.players = players;
        this.gamesPlayed = 0;
        this.roundsPlayed = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void incrementRoundsPlayed(int rounds) {
        this.roundsPlayed += rounds;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementLosses() {
        this.losses++;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

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
}
