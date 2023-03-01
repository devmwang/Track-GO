package model;

import java.util.ArrayList;

import persistence.*;
import exceptions.*;

// Represents the application data to be tracked by the application
public class AppData {
    private static final String DATA_STORE_PATH = "./data/app_data.json";
    private StoreReader storeReader;
    private ArrayList<Player> players;
    private ArrayList<Roster> rosters;
    private ArrayList<Match> matches;
    private int nextMatchId;

    // EFFECTS: Constructs a new AppData object with empty players, rosters, and matches, and nextMatchId = 0
    public AppData() {
        this.storeReader = new StoreReader(DATA_STORE_PATH);

        this.players = new ArrayList<>();
        this.rosters = new ArrayList<>();
        this.matches = new ArrayList<>();
        nextMatchId = 0;
    }

    // EFFECTS: Loads data from data store updates active application data
    public void loadFromFile() {
        try {

        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Roster> getRosters() {
        return rosters;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    // REQUIRES: username is not null
    // EFFECTS: Returns Player with username, throws PlayerNotFoundException if not found
    public Player getPlayerByUsername(String username) throws PlayerNotFoundException {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }

        throw new PlayerNotFoundException();
    }

    // REQUIRES: id is not null
    // EFFECTS: Returns Roster with id, throws RosterNotFoundException if not found
    public Roster getRosterById(String id) throws RosterNotFoundException {
        for (Roster roster : rosters) {
            if (roster.getId().equals(id)) {
                return roster;
            }
        }

        throw new RosterNotFoundException();
    }

    // REQUIRES: matchId is not null
    // EFFECTS: Returns Match with matchId, throws MatchNotFoundException if not found
    public Match getMatchById(int matchId) throws MatchNotFoundException {
        for (Match match : matches) {
            if (match.getMatchId() == matchId) {
                return match;
            }
        }

        throw new MatchNotFoundException();
    }

    // REQUIRES: username is not null or empty
    // MODIFIES: this
    // EFFECTS: Creates Player with provided username and adds created Player to players
    public void addPlayer(String username) {
        players.add(new Player(username));
    }

    // REQUIRES: id is not null or empty, playersArrayList is not null and size >= 1
    // MODIFIES: this
    // EFFECTS: Creates Roster with provided data and adds created Roster to rosters
    public void addRoster(String id, ArrayList<Player> playersArrayList) {
        rosters.add(new Roster(id, playersArrayList));
    }

    // REQUIRES: roster is not null and is in rosters
    // MODIFIES: this
    // EFFECTS: Deletes Roster from rosters
    public void deleteRoster(Roster roster) {
        rosters.remove(roster);
    }

    // REQUIRES: roster is in rosters, 16 >= wonRounds >= 0, 16 >= lostRounds >= 0
    // MODIFIES: this
    // EFFECTS: Creates Match, adds match to matches, and adjusts player and roster stats accordingly
    public void addMatch(Roster roster, int wonRounds, int lostRounds, String map) {
        matches.add(new Match(nextMatchId, roster, wonRounds, lostRounds, map));
        nextMatchId++;

        int totalRounds = wonRounds + lostRounds;

        roster.incrementGamesPlayed();
        roster.incrementRoundsPlayed(totalRounds);

        for (Player player : roster.getPlayers()) {
            player.incrementGamesPlayed();
            player.incrementRoundsPlayed(totalRounds);
        }

        if (wonRounds > lostRounds) {
            roster.incrementWins();

            for (Player player : roster.getPlayers()) {
                player.incrementWins();
            }
        } else if (wonRounds < lostRounds) {
            roster.incrementLosses();

            for (Player player : roster.getPlayers()) {
                player.incrementLosses();
            }
        }
    }
}
