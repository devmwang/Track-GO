package model;

import java.util.ArrayList;

// Represents a match to be tracked by the application
public class Match {
    private ArrayList<Player> players;
    private int roundsWon;
    private int roundsLost;

    // EFFECTS: Constructs a match with the provided roster and data values
    public Match(Roster roster, int roundsWon, int roundsLost) {
        this.players = roster.getPlayers();
        this.roundsWon = roundsWon;
        this.roundsLost = roundsLost;
    }

    public String getOverview() {
        return "";
    }

    public int getTotalRounds() {
        return roundsWon + roundsLost;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public int getRoundsLost() {
        return roundsLost;
    }
}
