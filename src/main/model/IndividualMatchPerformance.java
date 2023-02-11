package model;

public class IndividualMatchPerformance {
    private int totalDamageDealt;
    private int totalPoints;
    private int totalKills;
    private int totalDeaths;
    private int mostValuablePlayerAwards;

    public IndividualMatchPerformance(int totalDamageDealt, int totalPoints, int totalKills, int totalDeaths,
                                      int mostValuablePlayerAwards) {
        this.totalDamageDealt = totalDamageDealt;
        this.totalPoints = totalPoints;
        this.totalKills = totalKills;
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

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getMostValuablePlayerAwards() {
        return mostValuablePlayerAwards;
    }
}
