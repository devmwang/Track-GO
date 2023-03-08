// Tests adapted from CPSC 210 JSON Serialization Demo at
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonWriterTest.java

package persistence;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.MatchNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.*;

public class StoreWriterTest {
    private static final String TEST_FILES_ROOT_PATH = "./data/tests/";
    private static final String TEST_FILES_PATH = TEST_FILES_ROOT_PATH + "writer_test_file.json";

    @Test
    void testInvalidFile() {
        try {
            StoreWriter writer = new StoreWriter(TEST_FILES_ROOT_PATH + "my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // Expected
        }
    }

    @Test
    void testEmptyAppData() {
        try {
            StoreWriter storeWriter = new StoreWriter(TEST_FILES_PATH);
            storeWriter.open();
            storeWriter.write(new AppData());
            storeWriter.close();

            StoreReader storeReader = new StoreReader(TEST_FILES_PATH);
            AppData appData = new AppData();
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
        try {
            StoreWriter storeWriter = new StoreWriter(TEST_FILES_PATH);
            storeWriter.open();
            AppData appData = new AppData();
            appData.addPlayer("Player1");
            storeWriter.write(appData);
            storeWriter.close();

            StoreReader storeReader = new StoreReader(TEST_FILES_PATH);
            appData = new AppData();
            storeReader.read(appData);
            assertEquals(1, appData.getPlayers().size());
            assertEquals(0, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());

            assertEquals("Player1", appData.getPlayers().get(0).getUsername());
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
    void testMultiplePlayers() {
        try {
            StoreWriter storeWriter = new StoreWriter(TEST_FILES_PATH);
            storeWriter.open();
            AppData appData = new AppData();
            appData.addPlayer("Player1");
            appData.addPlayer("Player2");
            appData.addPlayer("Player3");
            storeWriter.write(appData);
            storeWriter.close();

            StoreReader storeReader = new StoreReader(TEST_FILES_PATH);
            appData = new AppData();
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
        try {
            StoreWriter storeWriter = new StoreWriter(TEST_FILES_PATH);
            storeWriter.open();
            AppData appData = new AppData();
            appData.addPlayer("Player1");
            appData.addPlayer("Player2");
            appData.addPlayer("Player3");
            ArrayList<Player> playerList = new ArrayList<>();
            playerList.add(appData.getPlayers().get(0));
            playerList.add(appData.getPlayers().get(1));
            playerList.add(appData.getPlayers().get(2));
            appData.addRoster("Roster1", playerList);
            storeWriter.write(appData);
            storeWriter.close();

            StoreReader storeReader = new StoreReader(TEST_FILES_PATH);
            appData = new AppData();
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(1, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());

            assertEquals("Roster1", appData.getRosters().get(0).getId());
            assertEquals(3, appData.getRosters().get(0).getPlayers().size());
            assertEquals("Player1", appData.getRosters().get(0).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getRosters().get(0).getPlayers().get(1).getUsername());
            assertEquals("Player3", appData.getRosters().get(0).getPlayers().get(2).getUsername());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testMultipleRosters() {
        try {
            StoreWriter storeWriter = new StoreWriter(TEST_FILES_PATH);
            storeWriter.open();
            AppData appData = new AppData();
            appData.addPlayer("Player1");
            appData.addPlayer("Player2");
            appData.addPlayer("Player3");
            ArrayList<Player> playerList = new ArrayList<>();
            playerList.add(appData.getPlayers().get(0));
            playerList.add(appData.getPlayers().get(1));
            appData.addRoster("Roster1", playerList);
            playerList = new ArrayList<>();
            playerList.add(appData.getPlayers().get(0));
            playerList.add(appData.getPlayers().get(1));
            playerList.add(appData.getPlayers().get(2));
            appData.addRoster("Roster2", playerList);
            storeWriter.write(appData);
            storeWriter.close();

            StoreReader storeReader = new StoreReader(TEST_FILES_PATH);
            appData = new AppData();
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(0, appData.getMatches().size());
            assertEquals(0, appData.getNextMatchId());

            assertEquals("Roster1", appData.getRosters().get(0).getId());
            assertEquals(2, appData.getRosters().get(0).getPlayers().size());
            assertEquals("Player1", appData.getRosters().get(0).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getRosters().get(0).getPlayers().get(1).getUsername());

            assertEquals("Roster2", appData.getRosters().get(1).getId());
            assertEquals(3, appData.getRosters().get(1).getPlayers().size());
            assertEquals("Player1", appData.getRosters().get(1).getPlayers().get(0).getUsername());
            assertEquals("Player2", appData.getRosters().get(1).getPlayers().get(1).getUsername());
            assertEquals("Player3", appData.getRosters().get(1).getPlayers().get(2).getUsername());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testSingleMatch() {
        try {
            StoreWriter storeWriter = new StoreWriter(TEST_FILES_PATH);
            storeWriter.open();
            AppData appData = new AppData();
            appData.addPlayer("Player1");
            appData.addPlayer("Player2");
            appData.addPlayer("Player3");
            ArrayList<Player> playerList = new ArrayList<>();
            playerList.add(appData.getPlayers().get(0));
            playerList.add(appData.getPlayers().get(1));
            playerList.add(appData.getPlayers().get(2));
            appData.addRoster("Roster1", playerList);
            appData.addMatch(appData.getRosters().get(0), 16, 10, "Dust2");
            storeWriter.write(appData);
            storeWriter.close();

            StoreReader storeReader = new StoreReader(TEST_FILES_PATH);
            appData = new AppData();
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(1, appData.getRosters().size());
            assertEquals(1, appData.getMatches().size());
            assertEquals(1, appData.getNextMatchId());

            assertEquals(0, appData.getMatches().get(0).getMatchId());
            assertEquals(16, appData.getMatches().get(0).getRoundsWon());
            assertEquals(10, appData.getMatches().get(0).getRoundsLost());
            assertEquals("Dust2", appData.getMatches().get(0).getMap());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testMultipleMatches() {
        try {
            StoreWriter storeWriter = new StoreWriter(TEST_FILES_PATH);
            storeWriter.open();
            AppData appData = new AppData();
            appData.addPlayer("Player1");
            appData.addPlayer("Player2");
            appData.addPlayer("Player3");
            ArrayList<Player> playerList = new ArrayList<>();
            playerList.add(appData.getPlayers().get(0));
            playerList.add(appData.getPlayers().get(1));
            appData.addRoster("Roster1", playerList);
            playerList = new ArrayList<>();
            playerList.add(appData.getPlayers().get(0));
            playerList.add(appData.getPlayers().get(1));
            playerList.add(appData.getPlayers().get(2));
            appData.addRoster("Roster2", playerList);
            appData.addMatch(appData.getRosters().get(0), 16, 10, "Dust2");
            appData.addMatch(appData.getRosters().get(0), 16, 12, "Mirage");
            appData.addMatch(appData.getRosters().get(1), 10, 16, "Inferno");
            storeWriter.write(appData);
            storeWriter.close();

            StoreReader storeReader = new StoreReader(TEST_FILES_PATH);
            appData = new AppData();
            storeReader.read(appData);
            assertEquals(3, appData.getPlayers().size());
            assertEquals(2, appData.getRosters().size());
            assertEquals(3, appData.getMatches().size());
            assertEquals(3, appData.getNextMatchId());

            assertEquals(0, appData.getMatches().get(0).getMatchId());
            assertEquals(16, appData.getMatches().get(0).getRoundsWon());
            assertEquals(10, appData.getMatches().get(0).getRoundsLost());
            assertEquals("Dust2", appData.getMatches().get(0).getMap());

            assertEquals(1, appData.getMatches().get(1).getMatchId());
            assertEquals(16, appData.getMatches().get(1).getRoundsWon());
            assertEquals(12, appData.getMatches().get(1).getRoundsLost());
            assertEquals("Mirage", appData.getMatches().get(1).getMap());

            assertEquals(2, appData.getMatches().get(2).getMatchId());
            assertEquals(10, appData.getMatches().get(2).getRoundsWon());
            assertEquals(16, appData.getMatches().get(2).getRoundsLost());
            assertEquals("Inferno", appData.getMatches().get(2).getMap());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }

    @Test
    void testSingleMatchPerformance() {
        try {
            StoreWriter storeWriter = new StoreWriter(TEST_FILES_PATH);
            storeWriter.open();
            AppData appData = new AppData();
            appData.addPlayer("Player1");
            appData.addPlayer("Player2");
            appData.addPlayer("Player3");
            ArrayList<Player> playerList = new ArrayList<>();
            playerList.add(appData.getPlayers().get(0));
            playerList.add(appData.getPlayers().get(1));
            playerList.add(appData.getPlayers().get(2));
            appData.addRoster("Roster1", playerList);
            appData.addMatch(appData.getRosters().get(0), 16, 10, "Dust2");
            appData.getPlayers().get(0).setMatchStats(0, 2100, 50, 18, 5, 12, 2);
            storeWriter.write(appData);
            storeWriter.close();

            StoreReader storeReader = new StoreReader(TEST_FILES_PATH);
            appData = new AppData();
            storeReader.read(appData);

            Player player1 = appData.getPlayers().get(0);

            player1.getMatchStatsById(0);
            assertEquals(2100, player1.getMatchStatsById(0).getTotalDamageDealt());
            assertEquals(50, player1.getMatchStatsById(0).getTotalPoints());
            assertEquals(18, player1.getMatchStatsById(0).getTotalKills());
            assertEquals(5, player1.getMatchStatsById(0).getTotalAssists());
            assertEquals(12, player1.getMatchStatsById(0).getTotalDeaths());
            assertEquals(2, player1.getMatchStatsById(0).getMostValuablePlayerAwards());
        } catch (IOException e) {
            fail("Should not have thrown IOException");
        } catch (MatchNotFoundException e) {
            fail("Should not have thrown MatchNotFoundException");
        }
    }
}
