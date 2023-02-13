package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatchPerformanceTest {
    private MatchPerformance mp1;
    private MatchPerformance mp2;

    @BeforeEach
    void runBefore() {
        mp1 = new MatchPerformance(1000, 100, 15, 5, 10, 1);
        mp2 = new MatchPerformance(2000, 200, 25, 15, 10, 2);
    }

    @Test
    void testConstructor() {
        assertEquals(1000, mp1.getTotalDamageDealt());
        assertEquals(100, mp1.getTotalPoints());
        assertEquals(15, mp1.getTotalKills());
        assertEquals(5, mp1.getTotalAssists());
        assertEquals(10, mp1.getTotalDeaths());
        assertEquals(1, mp1.getMostValuablePlayerAwards());

        assertEquals(2000, mp2.getTotalDamageDealt());
        assertEquals(200, mp2.getTotalPoints());
        assertEquals(25, mp2.getTotalKills());
        assertEquals(15, mp2.getTotalAssists());
        assertEquals(10, mp2.getTotalDeaths());
        assertEquals(2, mp2.getMostValuablePlayerAwards());
    }

    @Test
    void testGetKD() {
        assertEquals("15/10", mp1.getKD());
        assertEquals("25/10", mp2.getKD());
    }
}
