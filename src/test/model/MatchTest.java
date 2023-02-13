package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MatchTest {
    private Match m1;
    private Match m2;
    private ArrayList<Player> players;

    @BeforeEach
    void runBefore() {
        players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));

        Roster r1 = new Roster("Roster 1", players);

        m1 = new Match(1, r1, 16, 10, "Dust2");
        m2 = new Match(2, r1, 2, 16, "Inferno");
    }

    @Test
    void testConstructor() {
        assertEquals(1, m1.getMatchId());
        assertEquals(2, m2.getMatchId());

        assertEquals(2, m1.getPlayers().size());
        assertEquals(2, m2.getPlayers().size());

        assertEquals(players, m1.getPlayers());
        assertEquals(players, m2.getPlayers());

        assertEquals(26, m1.getTotalRounds());
        assertEquals(18, m2.getTotalRounds());

        assertEquals(16, m1.getRoundsWon());
        assertEquals(2, m2.getRoundsWon());

        assertEquals(10, m1.getRoundsLost());
        assertEquals(16, m2.getRoundsLost());

        assertEquals("Dust2", m1.getMap());
        assertEquals("Inferno", m2.getMap());
    }
}
