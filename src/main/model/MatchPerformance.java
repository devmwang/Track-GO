package model;

public class MatchPerformance {
    private final int totalDamageDealt;
    private final int totalPoints;
    private final int totalKills;
    private final int totalAssists;
    private final int totalDeaths;
    private final int mostValuablePlayerAwards;

    public MatchPerformance(int totalDamageDealt, int totalPoints, int totalKills, int totalAssists,
                            int totalDeaths, int mostValuablePlayerAwards) {
        this.totalDamageDealt = totalDamageDealt;
        this.totalPoints = totalPoints;
        this.totalKills = totalKills;
        this.totalAssists = totalAssists;
        this.totalDeaths = totalDeaths;
        this.mostValuablePlayerAwards = mostValuablePlayerAwards;
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

    // EFFECTS: Returns the KD ratio in the form of "K/D"
    public String getKD() {
        return totalKills + "/" + totalDeaths;
    }
}
