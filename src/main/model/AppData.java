package model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;
import persistence.Readable;
import exceptions.*;

// Represents the application data to be tracked by the application
public class AppData implements Writable, Readable {
    private ArrayList<Player> players;
    private ArrayList<Roster> rosters;
    private ArrayList<Match> matches;
    private int nextMatchId;

    // EFFECTS: Constructs a new AppData object with empty players, rosters, and matches, and nextMatchId = 0
    public AppData() {
        this.players = new ArrayList<>();
        this.rosters = new ArrayList<>();
        this.matches = new ArrayList<>();
        nextMatchId = 0;
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

    // EFFECTS: Converts app data to JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        // Add players
        JSONArray jsonArray = new JSONArray();
        for (Player player : players) {
            jsonArray.put(player.toJson());
        }
        json.put("players", jsonArray);

        // Add rosters
        jsonArray = new JSONArray();
        for (Roster roster : rosters) {
            jsonArray.put(roster.toJson());
        }
        json.put("rosters", jsonArray);

        // Add matches
        jsonArray = new JSONArray();
        for (Match match : matches) {
            jsonArray.put(match.toJson());
        }
        json.put("matches", jsonArray);

        return json;
    }

    // REQUIRES: jsonObject with valid and correct data
    // MODIFIES: this
    // EFFECTS: Handles loading app data from JSON object
    @Override
    public void fromJson(JSONObject jsonObject) {
        players = new ArrayList<>();
        rosters = new ArrayList<>();
        matches = new ArrayList<>();

        loadPlayers(jsonObject.getJSONArray("players"));
        loadRosters(jsonObject.getJSONArray("rosters"));
        loadMatches(jsonObject.getJSONArray("matches"));
    }

    // REQUIRES: jsonObject with valid and correct data
    // MODIFIES: this
    // EFFECTS: Loads player data from JSON object
    private void loadPlayers(JSONArray jsonArray) {
        for (Object object : jsonArray) {
            JSONObject playerObject = (JSONObject) object;

            Player player = new Player(playerObject.getString("username"));
            player.fromJson(playerObject);
            players.add(player);
        }
    }

    // REQUIRES: jsonObject with valid and correct data
    // MODIFIES: this
    // EFFECTS: Loads roster data from JSON object
    private void loadRosters(JSONArray jsonArray) {
        for (Object object : jsonArray) {
            JSONObject rosterObject = (JSONObject) object;

            ArrayList<String> playerUsernames = new ArrayList<>();
            for (Object obj : rosterObject.getJSONArray("players")) {
                playerUsernames.add((String) obj);
            }

            ArrayList<Player> playerList = new ArrayList<>();
            for (String username : playerUsernames) {
                try {
                    playerList.add(getPlayerByUsername(username));
                } catch (PlayerNotFoundException e) {
                    System.out.println("Player not found");
                }
            }

            Roster roster = new Roster(rosterObject.getString("id"), playerList);
            roster.fromJson(rosterObject);
            rosters.add(roster);
        }
    }

    // REQUIRES: jsonObject with valid and correct data
    // MODIFIES: this
    // EFFECTS: Loads match data from JSON object
    private void loadMatches(JSONArray jsonArray) {
        for (Object object : jsonArray) {
            JSONObject matchObject = (JSONObject) object;

            ArrayList<String> playerUsernames = new ArrayList<>();
            for (Object obj : matchObject.getJSONArray("players")) {
                playerUsernames.add((String) obj);
            }

            ArrayList<Player> playerList = new ArrayList<>();
            for (String username : playerUsernames) {
                try {
                    playerList.add(getPlayerByUsername(username));
                } catch (PlayerNotFoundException e) {
                    System.out.println("Player not found");
                }
            }

            Match match = new Match(matchObject.getInt("matchId"), playerList, matchObject.getInt("roundsWon"),
                    matchObject.getInt("roundsLost"), matchObject.getString("map"));
            matches.add(match);
        }
    }
}
