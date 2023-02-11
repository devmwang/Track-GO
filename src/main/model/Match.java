package model;

import java.util.HashMap;

// Represents a match to be tracked by the application
public class Match {
    private int matchId;
    private HashMap<Player, IndividualMatchPerformance> playerDetails;
    private int roundsWon;
    private int roundsLost;
    private String map;

    // EFFECTS: Constructs a match with the provided roster and data values
    public Match(int matchId, Roster roster, int roundsWon, int roundsLost, String map) {
        this.matchId = matchId;
        playerDetails = new HashMap<>();
        for (Player player : roster.getPlayers()) {
            playerDetails.put(player, null);
        }

        this.roundsWon = roundsWon;
        this.roundsLost = roundsLost;
        this.map = map;
    }

    public void setIndividualPerformance(Player player, int totalDamageDealt, int totalPoints, int totalKills,
                                         int totalDeaths, int mostValuablePlayerAwards) {
        playerDetails.replace(player, new IndividualMatchPerformance(totalDamageDealt, totalPoints, totalKills,
                totalDeaths, mostValuablePlayerAwards));
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

    public String getMap() {
        return map;
    }

    public int getMyMostValuablePlayerAwards(Player player) {
        return playerDetails.get(player).getMostValuablePlayerAwards();
    }

    public int getMyKills(Player player) {
        return playerDetails.get(player).getTotalKills();
    }

    public int getMyDeaths(Player player) {
        return playerDetails.get(player).getTotalDeaths();
    }

    public int getMyAverageDamagePerRound(Player player) {
        return playerDetails.get(player).getTotalDamageDealt() / getTotalRounds();
    }
}
