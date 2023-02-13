package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player p1;
    private Player p2;

    @BeforeEach
    void runBefore() {
        p1 = new Player("p1");
        p2 = new Player("p2");
    }

    @Test
    void testConstructor() {
        assertEquals("p1", p1.getUsername());
        assertEquals("p2", p2.getUsername());

        assertEquals(0, p1.getGamesPlayed());
        assertEquals(0, p2.getGamesPlayed());

        assertEquals(0, p1.getTotalDamageDealt());
        assertEquals(0, p2.getTotalDamageDealt());

        assertEquals(0, p1.getTotalPoints());
        assertEquals(0, p2.getTotalPoints());

        assertEquals(0, p1.getTotalKills());
        assertEquals(0, p2.getTotalKills());

        assertEquals(0, p1.getTotalAssists());
        assertEquals(0, p2.getTotalAssists());

        assertEquals(0, p1.getTotalDeaths());
        assertEquals(0, p2.getTotalDeaths());

        assertEquals(0, p1.getMostValuablePlayerAwards());
        assertEquals(0, p2.getMostValuablePlayerAwards());
    }

    @Test
    void testSetUsername() {
        p1.setUsername("p1-new");
        assertEquals("p1-new", p1.getUsername());

        p2.setUsername("p2-new");
        assertEquals("p2-new", p2.getUsername());

        p2.setUsername("p2-even-more-new");
        assertEquals("p2-even-more-new", p2.getUsername());
    }

    @Test
    void testSetMatchStats() {
        p1.setMatchStats(0, 1000, 21, 10, 1, 5, 1);

        try {
            MatchPerformance perf = p1.getMatchStatsById(0);

            assertEquals(1000, perf.getTotalDamageDealt());
            assertEquals(21, perf.getTotalPoints());
            assertEquals(10, perf.getTotalKills());
            assertEquals(1, perf.getTotalAssists());
            assertEquals(5, perf.getTotalDeaths());
            assertEquals(1, perf.getMostValuablePlayerAwards());
        } catch (MatchNotFoundException e) {
            fail("MatchNotFoundException should not have been thrown");
        }
    }

    @Test
    void testGetMatchStatsInvalid() {
        try {
            p1.getMatchStatsById(10000);

            fail("MatchNotFoundException should have been thrown");
        } catch (MatchNotFoundException e) {
            // expected
        }
    }

    @Test
    void testIncrementGamesPlayed() {
        p1.incrementGamesPlayed();
        assertEquals(1, p1.getGamesPlayed());

        p1.incrementGamesPlayed();
        assertEquals(2, p1.getGamesPlayed());

        p1.incrementGamesPlayed();
        assertEquals(3, p1.getGamesPlayed());
    }

    @Test
    void testIncrementRoundsPlayed() {
        p1.incrementRoundsPlayed(5);
        assertEquals(5, p1.getRoundsPlayed());

        p1.incrementRoundsPlayed(10);
        assertEquals(15, p1.getRoundsPlayed());

        p1.incrementRoundsPlayed(15);
        assertEquals(30, p1.getRoundsPlayed());
    }

    @Test
    void testIncrementWins() {
        p1.incrementWins();
        assertEquals(1, p1.getWins());

        p1.incrementWins();
        assertEquals(2, p1.getWins());

        p1.incrementWins();
        assertEquals(3, p1.getWins());
    }

    @Test
    void testIncrementLosses() {
        p1.incrementLosses();
        assertEquals(1, p1.getLosses());

        p1.incrementLosses();
        assertEquals(2, p1.getLosses());

        p1.incrementLosses();
        assertEquals(3, p1.getLosses());
    }


}