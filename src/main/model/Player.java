package model;

// Represents an individual player to be tracked by the application
public class Player {
    private String username;
    private int gamesPlayed;
    private int roundsPlayed;
    private int totalDamageDealt;
    private int totalPoints;
    private int totalMostValuablePlayerAwards;
    private int totalEnemiesFlashed;

    // REQUIRES: username is not null
    // EFFECTS: Constructs a player with the provided username and default (0) values for remaining fields
    public Player(String username) {
        this.username = username;
        this.gamesPlayed = 0;
        this.roundsPlayed = 0;
        this.totalDamageDealt = 0;
        this.totalPoints = 0;
        this.totalMostValuablePlayerAwards = 0;
        this.totalEnemiesFlashed = 0;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOverview() {
        return username
                + " | "
                + gamesPlayed + (gamesPlayed == 1 ? " Game Played | " : " Games Played | ")
                + roundsPlayed + (roundsPlayed == 1 ? " Round Played | " : " Rounds Played | ")
                + totalMostValuablePlayerAwards + (totalMostValuablePlayerAwards == 1 ? " MVP" : " MVPs");
    }

    public String getUsername() {
        return username;
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

    public int getTotalMostValuablePlayerAwards() {
        return totalMostValuablePlayerAwards;
    }

    public int getTotalEnemiesFlashed() {
        return totalEnemiesFlashed;
    }
}
