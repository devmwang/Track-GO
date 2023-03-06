package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a collection of performance stats for a player in a given match
public class MatchPerformance implements Writable {
    private final int totalDamageDealt;
    private final int totalPoints;
    private final int totalKills;
    private final int totalAssists;
    private final int totalDeaths;
    private final int mostValuablePlayerAwards;

    // EFFECTS: Constructs new match performance with the provided stats
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("totalDamageDealt", totalDamageDealt);
        json.put("totalPoints", totalPoints);
        json.put("totalKills", totalKills);
        json.put("totalAssists", totalAssists);
        json.put("totalDeaths", totalDeaths);
        json.put("mostValuablePlayerAwards", mostValuablePlayerAwards);

        return json;
    }
}
