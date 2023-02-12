package model;

import java.util.HashMap;

// Represents an individual player to be tracked by the application
public class Player {
    private String username = "";
    private HashMap<Integer, MatchPerformance> matchStats = new HashMap<>();
    private int gamesPlayed;
    private int roundsPlayed;
    private int wins;
    private int losses;
    private int totalDamageDealt;
    private int totalPoints;
    private int totalKills;
    private int totalAssists;
    private int totalDeaths;
    private int mostValuablePlayerAwards;
    private int totalEnemiesFlashed;

    // REQUIRES: username is not null
    // EFFECTS: Constructs a player with the provided username and default (0) values for remaining fields
    public Player(String username) {
        this.username = username;
        this.matchStats = new HashMap<>();
        this.gamesPlayed = 0;
        this.roundsPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.totalDamageDealt = 0;
        this.totalPoints = 0;
        this.totalKills = 0;
        this.totalAssists = 0;
        this.totalDeaths = 0;
        this.mostValuablePlayerAwards = 0;
    }

    // REQUIRES: username is not null
    // MODIFIES: this
    // EFFECTS: Sets the username of the player to the provided value
    public void setUsername(String username) {
        this.username = username;
    }

    // REQUIRES: matchId, damage, points, kills, assists, deaths, mostValuablePlayerAwards are all not null
    // MODIFIES: this
    // EFFECTS: Sets the match stats of the player for the matchId to the provided values and updates total stats
    public void setMatchStats(int matchId, int damage, int points, int kills, int assists, int deaths,
                              int mostValuablePlayerAwards) {
        matchStats.put(matchId, new MatchPerformance(damage, points, kills, assists, deaths, mostValuablePlayerAwards));

        this.totalDamageDealt += damage;
        this.totalPoints += points;
        this.totalKills += kills;
        this.totalAssists += assists;
        this.totalDeaths += deaths;
        this.mostValuablePlayerAwards += mostValuablePlayerAwards;
    }

    public String getUsername() {
        return username;
    }

    public MatchPerformance getMatchStatsById(int matchId) throws MatchNotFoundException {
        if (!matchStats.containsKey(matchId)) {
            throw new MatchNotFoundException();
        }
        return matchStats.get(matchId);
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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public int getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getMostValuablePlayerAwards() {
        return mostValuablePlayerAwards;
    }
}
