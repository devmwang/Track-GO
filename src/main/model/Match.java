package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

// Represents a match to be tracked by the application
public class Match {
    private int matchId;
    private ArrayList<Player> players;
    private int roundsWon;
    private int roundsLost;
    private String map;

    // EFFECTS: Constructs a match with the provided roster and data values
    public Match(int matchId, Roster roster, int roundsWon, int roundsLost, String map) {
        this.matchId = matchId;
        this.players = new ArrayList<>();
        this.players.addAll(roster.getPlayers());
        this.roundsWon = roundsWon;
        this.roundsLost = roundsLost;
        this.map = map;
    }

//    public void setIndividualPerformance(Player player, int totalDamageDealt, int totalPoints, int totalKills,
//                                         int totalAssists, int totalDeaths, int mostValuablePlayerAwards) {
//        playerDetails.replace(player, new IndividualMatchPerformance(totalDamageDealt, totalPoints, totalKills,
//                totalAssists, totalDeaths, mostValuablePlayerAwards));
//    }

    public int getMatchId() {
        return matchId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
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
}
