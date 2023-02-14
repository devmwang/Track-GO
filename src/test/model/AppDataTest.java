package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppDataTest {
    private AppData appData;

    @BeforeEach
    void runBefore() {
        appData = new AppData();
    }

    @Test
    void testConstructor() {
        assertEquals(0, appData.getPlayers().size());
        assertEquals(0, appData.getRosters().size());
        assertEquals(0, appData.getMatches().size());
    }

    @Test
    void testAddPlayer() {
        appData.addPlayer("TestPlayer");

        assertEquals(1, appData.getPlayers().size());
        assertEquals("TestPlayer", appData.getPlayers().get(0).getUsername());
    }

    @Test
    void testAddTwoPlayers() {
        appData.addPlayer("TestPlayer1");
        appData.addPlayer("TestPlayer2");

        assertEquals(2, appData.getPlayers().size());
        assertEquals("TestPlayer1", appData.getPlayers().get(0).getUsername());
        assertEquals("TestPlayer2", appData.getPlayers().get(1).getUsername());
    }

    @Test
    void getPlayerByUsername() {
        appData.addPlayer("TestPlayer");

        try {
            Player result = appData.getPlayerByUsername("TestPlayer");
            assertEquals("TestPlayer", result.getUsername());
        } catch (PlayerNotFoundException e) {
            fail("PlayerNotFoundException should not have been thrown");
        }
    }

    @Test
    void getPlayerByUsernameFail() {
        try {
            Player result = appData.getPlayerByUsername("TestPlayer");

            fail("PlayerNotFoundException should have been thrown");
        } catch (PlayerNotFoundException e) {
            // expected
        }
    }

    @Test
    void testAddRoster() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("TestPlayer1"));
        players.add(new Player("TestPlayer2"));

        appData.addRoster("TestRoster", players);

        assertEquals(1, appData.getRosters().size());

        try {
            Roster result = appData.getRosterById("TestRoster");

            assertEquals("TestRoster", result.getId());
            assertEquals(players, result.getPlayers());
        } catch (RosterNotFoundException e) {
            fail("RosterNotFoundException should not have been thrown");
        }
    }

    @Test
    void testDeleteRoster() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("TestPlayer1"));
        players.add(new Player("TestPlayer2"));

        appData.addRoster("TestRoster", players);

        assertEquals(1, appData.getRosters().size());

        try {
            Roster roster = appData.getRosterById("TestRoster");

            appData.deleteRoster(roster);

            assertEquals(0, appData.getRosters().size());
        } catch (RosterNotFoundException e) {
            fail("RosterNotFoundException should not have been thrown");
        }
    }

    @Test
    void testGetRosterByIdFail() {
        try {
            Roster roster = appData.getRosterById("TestRoster");

            fail("RosterNotFoundException should have been thrown");
        } catch (RosterNotFoundException e) {
            // expected
        }
    }

    @Test
    void testAddMatchWin() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("TestPlayer"));
        Roster roster = new Roster("TestRoster", players);

        appData.addMatch(roster, 16, 10, "TestMap");

        assertEquals(1, appData.getMatches().size());

        try {
            Match result = appData.getMatchById(0);

            assertEquals(0, result.getMatchId());
        } catch (MatchNotFoundException e) {
            fail("MatchNotFoundException should not have been thrown");
        }

        assertEquals(1, roster.getGamesPlayed());
        assertEquals(26, roster.getRoundsPlayed());
        assertEquals(1, roster.getWins());
        assertEquals(0, roster.getLosses());

        assertEquals(1, players.get(0).getGamesPlayed());
        assertEquals(26, players.get(0).getRoundsPlayed());
        assertEquals(1, players.get(0).getWins());
        assertEquals(0, players.get(0).getLosses());
    }

    @Test
    void testAddMatchLoss() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("TestPlayer"));
        Roster roster = new Roster("TestRoster", players);

        appData.addMatch(roster, 10, 16, "TestMap");

        assertEquals(1, appData.getMatches().size());

        try {
            Match result = appData.getMatchById(0);

            assertEquals(0, result.getMatchId());
        } catch (MatchNotFoundException e) {
            fail("MatchNotFoundException should not have been thrown");
        }

        assertEquals(1, roster.getGamesPlayed());
        assertEquals(26, roster.getRoundsPlayed());
        assertEquals(0, roster.getWins());
        assertEquals(1, roster.getLosses());

        assertEquals(1, players.get(0).getGamesPlayed());
        assertEquals(26, players.get(0).getRoundsPlayed());
        assertEquals(0, players.get(0).getWins());
        assertEquals(1, players.get(0).getLosses());
    }

    @Test
    void testAddMatchTie() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("TestPlayer"));
        Roster roster = new Roster("TestRoster", players);

        appData.addMatch(roster, 15, 15, "TestMap");

        assertEquals(1, appData.getMatches().size());

        try {
            Match result = appData.getMatchById(0);

            assertEquals(0, result.getMatchId());
        } catch (MatchNotFoundException e) {
            fail("MatchNotFoundException should not have been thrown");
        }

        assertEquals(1, roster.getGamesPlayed());
        assertEquals(30, roster.getRoundsPlayed());
        assertEquals(0, roster.getWins());
        assertEquals(0, roster.getLosses());

        assertEquals(1, players.get(0).getGamesPlayed());
        assertEquals(30, players.get(0).getRoundsPlayed());
        assertEquals(0, players.get(0).getWins());
        assertEquals(0, players.get(0).getLosses());
    }

    @Test
    void testAddTwoMatches() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("TestPlayer"));
        Roster roster = new Roster("TestRoster", players);

        appData.addMatch(roster, 16, 10, "TestMap");
        appData.addMatch(roster, 10, 16, "OtherTestMap");

        assertEquals(2, appData.getMatches().size());

        try {
            Match result1 = appData.getMatchById(0);
            Match result2 = appData.getMatchById(1);

            assertEquals(0, result1.getMatchId());
            assertEquals(1, result2.getMatchId());
        } catch (MatchNotFoundException e) {
            fail("MatchNotFoundException should not have been thrown");
        }

        assertEquals(2, roster.getGamesPlayed());
        assertEquals(52, roster.getRoundsPlayed());
        assertEquals(1, roster.getWins());
        assertEquals(1, roster.getLosses());

        assertEquals(2, players.get(0).getGamesPlayed());
        assertEquals(52, players.get(0).getRoundsPlayed());
        assertEquals(1, players.get(0).getWins());
        assertEquals(1, players.get(0).getLosses());
    }

    @Test
    void getMatchByIdFail() {
        try {
            Match result = appData.getMatchById(1000);

            fail("MatchNotFoundException should have been thrown");
        } catch (MatchNotFoundException e) {
            // expected
        }
    }
}
