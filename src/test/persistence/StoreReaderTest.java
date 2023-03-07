// Tests adapted from CPSC 210 JSON Serialization Demo at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java

package persistence;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.*;


public class StoreReaderTest {
    private static final String TEST_FILES_ROOT_PATH = "./data/tests/";
    private AppData appData;

    @BeforeEach
    void runBefore() {
        appData = new AppData();
    }

    @Test
    void testFileDoesNotExist() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "does_not_exist.json");

        try {
            storeReader.read(appData);
            fail("IOException expected");
        } catch (IOException e) {
            // Expected
        }
    }

    @Test
    void testEmptyAppData() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "empty_app_data.json");

        try {
            storeReader.read(appData);
            assertEquals(0, appData.getPlayers().size());
            assertEquals(0, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testSinglePlayer() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "single_player.json");

        try {
            storeReader.read(appData);
            assertEquals(1, appData.getPlayers().size());
            assertEquals(0, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());

            assertEquals("SinglePlayer", appData.getPlayers().get(0).getUsername());
            assertEquals(0, appData.getPlayers().get(0).getGamesPlayed());
            assertEquals(0, appData.getPlayers().get(0).getRoundsPlayed());
            assertEquals(0, appData.getPlayers().get(0).getWins());
            assertEquals(0, appData.getPlayers().get(0).getLosses());
            assertEquals(0, appData.getPlayers().get(0).getTotalDamageDealt());
            assertEquals(0, appData.getPlayers().get(0).getTotalPoints());
            assertEquals(0, appData.getPlayers().get(0).getTotalKills());
            assertEquals(0, appData.getPlayers().get(0).getTotalAssists());
            assertEquals(0, appData.getPlayers().get(0).getTotalDeaths());
            assertEquals(0, appData.getPlayers().get(0).getMostValuablePlayerAwards());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testSinglePlayerWithStats() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "single_player_with_stats.json");

        try {
            storeReader.read(appData);
            assertEquals(1, appData.getPlayers().size());
            assertEquals(0, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());

            assertEquals("SinglePlayer", appData.getPlayers().get(0).getUsername());
            assertEquals(5, appData.getPlayers().get(0).getGamesPlayed());
            assertEquals(9, appData.getPlayers().get(0).getRoundsPlayed());
            assertEquals(1, appData.getPlayers().get(0).getWins());
            assertEquals(10, appData.getPlayers().get(0).getLosses());
            assertEquals(6, appData.getPlayers().get(0).getTotalDamageDealt());
            assertEquals(8, appData.getPlayers().get(0).getTotalPoints());
            assertEquals(3, appData.getPlayers().get(0).getTotalKills());
            assertEquals(2, appData.getPlayers().get(0).getTotalAssists());
            assertEquals(7, appData.getPlayers().get(0).getTotalDeaths());
            assertEquals(4, appData.getPlayers().get(0).getMostValuablePlayerAwards());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testMultiplePlayers() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "multiple_players.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(0, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());

            assertEquals("Player1", appData.getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getPlayers().get(1).getUsername());
            assertEquals("Player3", appData.getPlayers().get(2).getUsername());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testSingleRoster() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "single_roster.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(1, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());

            assertEquals("SingleRoster", appData.getRosters().get(0).getId());
            assertEquals("Player1", appData.getRosters().get(0).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getRosters().get(0).getPlayers().get(1).getUsername());
            assertEquals("Player3", appData.getRosters().get(0).getPlayers().get(2).getUsername());
            assertEquals(0, appData.getRosters().get(0).getGamesPlayed());
            assertEquals(0, appData.getRosters().get(0).getRoundsPlayed());
            assertEquals(0, appData.getRosters().get(0).getWins());
            assertEquals(0, appData.getRosters().get(0).getLosses());
            assertEquals(0, appData.getRosters().get(0).getTies());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testMultipleRosters() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "multiple_rosters.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());

            assertEquals("Roster1", appData.getRosters().get(0).getId());
            assertEquals("Roster2", appData.getRosters().get(1).getId());

            assertEquals("Player1", appData.getRosters().get(0).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getRosters().get(0).getPlayers().get(1).getUsername());

            assertEquals("Player1", appData.getRosters().get(1).getPlayers().get(0).getUsername());
            assertEquals("Player3", appData.getRosters().get(1).getPlayers().get(1).getUsername());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testSingleMatch() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "single_match.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(1, appData.getMatches().size());
            assertEquals(1, appData.getNextMatchId());

            assertEquals(0, appData.getMatches().get(0).getMatchId());
            assertEquals("Player1", appData.getMatches().get(0).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getMatches().get(0).getPlayers().get(1).getUsername());

            assertEquals(16, appData.getMatches().get(0).getRoundsWon());
            assertEquals(10, appData.getMatches().get(0).getRoundsLost());
            assertEquals("Dust2", appData.getMatches().get(0).getMap());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testMultipleMatches() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "multiple_matches.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(2, appData.getMatches().size());
            assertEquals(2, appData.getNextMatchId());

            assertEquals(0, appData.getMatches().get(0).getMatchId());
            assertEquals("Player1", appData.getMatches().get(0).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getMatches().get(0).getPlayers().get(1).getUsername());
            assertEquals(16, appData.getMatches().get(0).getRoundsWon());
            assertEquals(10, appData.getMatches().get(0).getRoundsLost());
            assertEquals("Dust2", appData.getMatches().get(0).getMap());

            assertEquals(1, appData.getMatches().get(1).getMatchId());
            assertEquals("Player1", appData.getMatches().get(1).getPlayers().get(0).getUsername());
            assertEquals("Player3", appData.getMatches().get(1).getPlayers().get(1).getUsername());
            assertEquals(10, appData.getMatches().get(1).getRoundsWon());
            assertEquals(16, appData.getMatches().get(1).getRoundsLost());
            assertEquals("Inferno", appData.getMatches().get(1).getMap());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }
}
