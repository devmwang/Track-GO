// Tests adapted from CPSC 210 JSON Serialization Demo at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java

package persistence;
import java.io.IOException;

import exceptions.MatchNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.*;
import exceptions.AppDataInvalidException;

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
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
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
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
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
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
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
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
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
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
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
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
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
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
        }
    }

    @Test
    void testMultipleMatches() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "multiple_matches.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(3, appData.getMatches().size());
            assertEquals(3, appData.getNextMatchId());

            assertEquals(0, appData.getMatches().get(0).getMatchId());
            assertEquals("Player1", appData.getMatches().get(0).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getMatches().get(0).getPlayers().get(1).getUsername());
            assertEquals(16, appData.getMatches().get(0).getRoundsWon());
            assertEquals(10, appData.getMatches().get(0).getRoundsLost());
            assertEquals("Dust2", appData.getMatches().get(0).getMap());

            assertEquals(1, appData.getMatches().get(1).getMatchId());
            assertEquals("Player1", appData.getMatches().get(1).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getMatches().get(1).getPlayers().get(1).getUsername());
            assertEquals(16, appData.getMatches().get(1).getRoundsWon());
            assertEquals(12, appData.getMatches().get(1).getRoundsLost());
            assertEquals("Mirage", appData.getMatches().get(1).getMap());

            assertEquals(2, appData.getMatches().get(2).getMatchId());
            assertEquals("Player1", appData.getMatches().get(2).getPlayers().get(0).getUsername());
            assertEquals("Player3", appData.getMatches().get(2).getPlayers().get(1).getUsername());
            assertEquals(10, appData.getMatches().get(2).getRoundsWon());
            assertEquals(16, appData.getMatches().get(2).getRoundsLost());
            assertEquals("Inferno", appData.getMatches().get(2).getMap());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
        }
    }

    @Test
    void testAllStatsPlayer1() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "all_stats.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(2, appData.getMatches().size());
            assertEquals(2, appData.getNextMatchId());

            Player player1 = appData.getPlayers().get(0);

            // Player1 has match data for both matches
            assertEquals("Player1", player1.getUsername());
            assertEquals(2, player1.getGamesPlayed());
            assertEquals(52, player1.getRoundsPlayed());
            assertEquals(1, player1.getWins());
            assertEquals(1, player1.getLosses());
            assertEquals(4970, player1.getTotalDamageDealt());
            assertEquals(90, player1.getTotalPoints());
            assertEquals(38, player1.getTotalKills());
            assertEquals(8, player1.getTotalAssists());
            assertEquals(25, player1.getTotalDeaths());
            assertEquals(5, player1.getMostValuablePlayerAwards());

            // Player1 is in and has match data for matches with id 0 and 1
            MatchPerformance m0 = player1.getMatchStatsById(0);
            assertEquals(2470, m0.getTotalDamageDealt());
            assertEquals(40, m0.getTotalPoints());
            assertEquals(16, m0.getTotalKills());
            assertEquals(4, m0.getTotalAssists());
            assertEquals(10, m0.getTotalDeaths());
            assertEquals(3, m0.getMostValuablePlayerAwards());

            MatchPerformance m1 = player1.getMatchStatsById(1);
            assertEquals(2500, m1.getTotalDamageDealt());
            assertEquals(50, m1.getTotalPoints());
            assertEquals(22, m1.getTotalKills());
            assertEquals(4, m1.getTotalAssists());
            assertEquals(15, m1.getTotalDeaths());
            assertEquals(2, m1.getMostValuablePlayerAwards());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        } catch (MatchNotFoundException e) {
            fail("Should not have thrown MatchNotFoundException");
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
        }
    }

    @Test
    void testAllStatsPlayer2() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "all_stats.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(2, appData.getMatches().size());
            assertEquals(2, appData.getNextMatchId());

            Player player2 = appData.getPlayers().get(1);

            // Player2 has match data for 1 match
            assertEquals("Player2", player2.getUsername());
            assertEquals(1, player2.getGamesPlayed());
            assertEquals(26, player2.getRoundsPlayed());
            assertEquals(1, player2.getWins());
            assertEquals(0, player2.getLosses());
            assertEquals(2000, player2.getTotalDamageDealt());
            assertEquals(38, player2.getTotalPoints());
            assertEquals(16, player2.getTotalKills());
            assertEquals(4, player2.getTotalAssists());
            assertEquals(14, player2.getTotalDeaths());
            assertEquals(2, player2.getMostValuablePlayerAwards());

            // Player2 is in and has match data for match with id 0
            MatchPerformance m0 = player2.getMatchStatsById(0);
            assertEquals(2000, m0.getTotalDamageDealt());
            assertEquals(38, m0.getTotalPoints());
            assertEquals(16, m0.getTotalKills());
            assertEquals(4, m0.getTotalAssists());
            assertEquals(14, m0.getTotalDeaths());
            assertEquals(2, m0.getMostValuablePlayerAwards());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        } catch (MatchNotFoundException e) {
            fail("Should not have thrown MatchNotFoundException");
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
        }
    }

    @Test
    void testAllStatsPlayer3() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "all_stats.json");

        try {
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(2, appData.getMatches().size());
            assertEquals(2, appData.getNextMatchId());

            Player player3 = appData.getPlayers().get(2);

            // Player3 has no match performance data, does have general data
            assertEquals("Player3", player3.getUsername());
            assertEquals(1, player3.getGamesPlayed());
            assertEquals(26, player3.getRoundsPlayed());
            assertEquals(0, player3.getWins());
            assertEquals(1, player3.getLosses());
            assertEquals(0, player3.getTotalDamageDealt());
            assertEquals(0, player3.getTotalPoints());
            assertEquals(0, player3.getTotalKills());
            assertEquals(0, player3.getTotalAssists());
            assertEquals(0, player3.getTotalDeaths());
            assertEquals(0, player3.getMostValuablePlayerAwards());

            // Player is in match with id 1 but has no performance data
            player3.getMatchStatsById(1);

            fail("Should have thrown MatchNotFoundException");
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        } catch (MatchNotFoundException e) {
            // Expected
        } catch (AppDataInvalidException e) {
            fail("Should not have thrown AppDataInvalidException");
        }
    }

    @Test
    void testInvalidPlayerInRoster() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "invalid_roster.json");

        try {
            storeReader.read(appData);
            fail("Should have thrown AppDataInvalidException");
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        } catch (AppDataInvalidException e) {
            // Expected
        }
    }

    @Test
    void testInvalidPlayerInMatch() {
        StoreReader storeReader = new StoreReader(TEST_FILES_ROOT_PATH + "invalid_roster.json");

        try {
            storeReader.read(appData);
            fail("Should have thrown AppDataInvalidException");
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        } catch (AppDataInvalidException e) {
            // Expected
        }
    }
}
