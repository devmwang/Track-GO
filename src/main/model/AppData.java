package model;

import java.util.ArrayList;

public class AppData {
    private ArrayList<Player> players;
    private ArrayList<Roster> rosters;
    private ArrayList<Match> matches;

    public AppData() {
        this.players = new ArrayList<>();
        this.rosters = new ArrayList<>();
        this.matches = new ArrayList<>();
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

    // REQUIRES: Player in players has username
    // EFFECTS: Returns player with username
    public Player getPlayerByUsername(String username) throws PlayerNotFoundException {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }

        throw new PlayerNotFoundException();
    }

    // REQUIRES: Roster in rosters has id
    // EFFECTS: Returns roster with id
    public Roster getRosterById(String id) throws RosterNotFoundException {
        for (Roster roster : rosters) {
            if (roster.getId().equals(id)) {
                return roster;
            }
        }

        throw new RosterNotFoundException();
    }

    // REQUIRES: username
    // EFFECTS: Creates player with provided username adds created player to players
    public void addPlayer(String username) {
        players.add(new Player(username));
    }

    // REQUIRES: id, playersArrayList
    // EFFECTS: Creates roster with provided data and adds created roster to rosters
    public void addRoster(String id, ArrayList<Player> playersArrayList) {
        rosters.add(new Roster(id, playersArrayList));
    }

    // REQUIRES: roster is in rosters, wonRounds >= 0, lostRounds >= 0
    // EFFECTS: Adds provided match to matches
    public void addMatch(Roster roster, int wonRounds, int lostRounds) {
        matches.add(new Match(roster, wonRounds, lostRounds));
    }

    // REQUIRES: roster is in rosters
    // EFFECTS: Deletes roster from rosters
    public void deleteRoster(Roster roster) {
        rosters.remove(roster);
    }
}
