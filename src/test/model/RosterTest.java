package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class RosterTest {
    private Roster r1;
    private Roster r2;
    private ArrayList<Player> players1;
    private ArrayList<Player> players2;

    @BeforeEach
    void runBefore() {
        players1 = new ArrayList<>();
        players1.add(new Player("Player 1"));

        players2 = new ArrayList<>();
        players2.add(new Player("Player 1"));
        players2.add(new Player("Player 2"));
        players2.add(new Player("Player 3"));

        r1 = new Roster("Roster 1", players1);
        r2 = new Roster("Roster 2", players2);
    }

    @Test
    void testConstructor() {
        assertEquals("Roster 1", r1.getId());
        assertEquals("Roster 2", r2.getId());

        assertEquals(players1, r1.getPlayers());
        assertEquals(players2, r2.getPlayers());

        assertEquals(1, r1.getPlayers().size());
        assertEquals(3, r2.getPlayers().size());

        assertEquals(0, r1.getGamesPlayed());
        assertEquals(0, r2.getGamesPlayed());

        assertEquals(0, r1.getRoundsPlayed());
        assertEquals(0, r2.getRoundsPlayed());

        assertEquals(0, r1.getWins());
        assertEquals(0, r2.getWins());

        assertEquals(0, r1.getLosses());
        assertEquals(0, r2.getLosses());
    }

    @Test
    void testAddPlayer() {
        Player p1 = new Player("Player A1");
        Player p2 = new Player("Player A2");
        Player p3 = new Player("Player A3");

        r1.addPlayer(p1);

        r2.addPlayer(p1);
        r2.addPlayer(p2);
        r2.addPlayer(p3);

        assertEquals(2, r1.getPlayers().size());
        assertEquals(6, r2.getPlayers().size());

        assertEquals(p1, r1.getPlayers().get(1));

        assertEquals(p1, r2.getPlayers().get(3));
        assertEquals(p2, r2.getPlayers().get(4));
        assertEquals(p3, r2.getPlayers().get(5));
    }

    @Test
    void testRemovePlayer() {
        Player p1 = new Player("Player B1");
        Player p2 = new Player("Player B2");
        Player p3 = new Player("Player B3");

        r1.addPlayer(p1);
        r1.addPlayer(p2);
        r1.addPlayer(p3);

        r2.addPlayer(p1);
        r2.addPlayer(p2);
        r2.addPlayer(p3);

        r1.removePlayer(p1);
        r1.removePlayer(p2);
        r1.removePlayer(p3);

        r2.removePlayer(p1);
        r2.removePlayer(p2);
        r2.removePlayer(p3);

        assertEquals(1, r1.getPlayers().size());
        assertEquals(3, r2.getPlayers().size());
    }

    @Test
    void testIncrementGamesPlayed() {
        r1.incrementGamesPlayed();
        r2.incrementGamesPlayed();

        assertEquals(1, r1.getGamesPlayed());
        assertEquals(1, r2.getGamesPlayed());

        r2.incrementGamesPlayed();
        assertEquals(2, r2.getGamesPlayed());
    }

    @Test
    void testIncrementRoundsPlayed() {
        r1.incrementRoundsPlayed(1);
        r2.incrementRoundsPlayed(1);

        assertEquals(1, r1.getRoundsPlayed());
        assertEquals(1, r2.getRoundsPlayed());

        r2.incrementRoundsPlayed(2);
        assertEquals(3, r2.getRoundsPlayed());
    }

    @Test
    void testIncrementWins() {
        r1.incrementWins();
        r2.incrementWins();

        assertEquals(1, r1.getWins());
        assertEquals(1, r2.getWins());

        r2.incrementWins();
        assertEquals(2, r2.getWins());
    }

    @Test
    void testIncrementLosses() {
        r1.incrementLosses();
        r2.incrementLosses();

        assertEquals(1, r1.getLosses());
        assertEquals(1, r2.getLosses());

        r2.incrementLosses();
        assertEquals(2, r2.getLosses());
    }

    @Test
    void testGetWinRate() {
        assertEquals(0, r1.getWinRate());

        r1.incrementGamesPlayed();
        r1.incrementGamesPlayed();

        assertEquals(0, r1.getWinRate());

        r2.incrementGamesPlayed();
        r2.incrementGamesPlayed();
        r2.incrementWins();

        assertEquals(50.0, r2.getWinRate());
    }

    @Test
    void testGetTies() {
        r1.incrementGamesPlayed();
        r1.incrementGamesPlayed();
        r1.incrementGamesPlayed();

        r1.incrementWins();
        r1.incrementLosses();

        assertEquals(1, r1.getTies());
    }
}
