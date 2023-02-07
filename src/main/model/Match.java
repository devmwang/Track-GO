package model;

// Represents a match to be tracked by the application
public class Match {
    private int roundsWon;
    private int roundsLost;

    // EFFECTS: Constructs a match with the provided roster and data values
    public Match(int roundsWon, int roundsLost) {
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
