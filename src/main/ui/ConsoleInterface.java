package ui;

import java.util.*;
import java.io.IOException;

import model.*;
import exceptions.*;
import persistence.*;

// Represents the console interface for the application
public class ConsoleInterface {
    private static final String DATA_STORE_PATH = "./data/app_data.json";
    private StoreReader storeReader;
    private StoreWriter storeWriter;
    private final Scanner scanner;
    private final AppData appData;

    // EFFECTS: Initializes app, displays welcome message and displays application main menu
    public ConsoleInterface() {
        this.storeReader = new StoreReader(DATA_STORE_PATH);
        this.storeWriter = new StoreWriter(DATA_STORE_PATH);

        this.scanner = new Scanner(System.in);
        this.appData = new AppData();

        System.out.println("Welcome to Track:GO!\n");
        displayMainMenu();
    }

    // REQUIRES: optionsText and commands are not null, optionsText and commands are the same size
    // EFFECTS: Generalized menu handler, handles user selection and displays appropriate menu
    private void handleMenu(ArrayList<String> optionsText, Map<String, Runnable> commands) {
        for (String option : optionsText) {
            System.out.println(option);
        }

        System.out.println();
        String select = scanner.nextLine();
        System.out.println();

        if (commands.containsKey(select)) {
            commands.get(select).run();
        } else {
            System.out.println("Invalid selection, try again. \n");
            handleMenu(optionsText, commands);
        }
    }

    // EFFECTS: Displays menu options and passes user selection to handleMenu
    private void displayMainMenu() {
        System.out.println("Select an option:");

        ArrayList<String> optionsText = new ArrayList<>();

        optionsText.add("[1] View matches");
        optionsText.add("[2] Add new match");
        optionsText.add("[3] View players");
        optionsText.add("[4] View rosters");
        optionsText.add("[5] Load app data from file");
        optionsText.add("[6] Save app data to file");
        optionsText.add("[7] Exit");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayMatchesOverviewMenu);
        commands.put("2", this::displayMatchAddMenu);
        commands.put("3", this::displayPlayersOverviewMenu);
        commands.put("4", this::displayRostersOverviewMenu);
        commands.put("5", this::handleLoadFromFile);
        commands.put("6", this::handleSaveToFile);
        commands.put("7", this::exit);

        handleMenu(optionsText, commands);
    }

    // EFFECTS: Handles loading data from file into application
    private void handleLoadFromFile() {
        try {
            storeReader.read();
            System.out.println("Data loaded successfully from " + DATA_STORE_PATH + ".");
        } catch (IOException e) {
            System.out.println("An error occurred while loading data from " + DATA_STORE_PATH + ".");
        }

        System.out.println("Returning to main menu. \n");
        displayMainMenu();
    }

    // EFFECTS: Handles saving data to file from application
    private void handleSaveToFile() {
        try {
            storeWriter.open();
            storeWriter.write(appData);
            storeWriter.close();
            System.out.println("Data saved successfully to " + DATA_STORE_PATH + ".");
        } catch (IOException e) {
            System.out.println("An error occurred while saving data to " + DATA_STORE_PATH + ".");
        }

        System.out.println("Returning to main menu. \n");
        displayMainMenu();
    }

    // EFFECTS: Displays matches overview
    private void displayMatchesOverviewMenu() {
        ArrayList<Match> matches = appData.getMatches();

        if (matches.isEmpty()) {
            System.out.println("No matches have been added yet. \n");
        } else {
            handleMatchesOverviewTable(matches);
        }

        ArrayList<String> optionsText = new ArrayList<>();

        optionsText.add("[1] View match details");
        optionsText.add("[2] Add new match");
        optionsText.add("[3] Edit match details");
        optionsText.add("[4] Back to main menu");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayMatchDetailsMenu);
        commands.put("2", this::displayMatchAddMenu);
        commands.put("3", this::displayMatchEditMenu);
        commands.put("4", this::displayMainMenu);

        handleMenu(optionsText, commands);
    }

    // REQUIRES: matches is not null
    // EFFECTS: Displays match overview table
    private void handleMatchesOverviewTable(ArrayList<Match> matches) {
        String overviewFormat = "| %-8d | %-15s | %-9s |%n";

        System.out.format("+----------+-----------------+-----------+%n");
        System.out.format("| Match ID | Map             | Score     |%n");
        System.out.format("+----------+-----------------+-----------+%n");
        for (Match match : matches) {
            System.out.format(overviewFormat,
                    match.getMatchId(),
                    match.getMap(),
                    match.getRoundsWon() + " - " + match.getRoundsLost());
        }
        System.out.format("+----------+-----------------+-----------+%n%n");
    }

    // EFFECTS: Displays match details selection
    private void displayMatchDetailsMenu() {
        System.out.println("Enter the match id: (Type \"cancel\" to return to matches overview) \n");
        String matchId = scanner.nextLine();

        if (matchId.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to matches overview. \n");
            displayMatchesOverviewMenu();
            return;
        }

        try {
            System.out.println();

            Match match = appData.getMatchById(Integer.parseInt(matchId));

            handleMatchDetailsMenu(match);
        } catch (MatchNotFoundException e) {
            System.out.println("\nInvalid match id. \n");
            displayMatchDetailsMenu();
        }
    }

    // REQUIRES: match is not null
    // EFFECTS: Displays match details menu
    private void handleMatchDetailsMenu(Match match) {
        handleMatchDetailsTable(match);

        System.out.println("[1] Edit match details");
        System.out.println("[2] Back to matches overview \n");

        String input = scanner.nextLine();

        if (input.equals("1")) {
            handleMatchEdit(match);
        } else if (input.equals("2")) {
            displayMatchesOverviewMenu();
        } else {
            System.out.println("Invalid selection, try again. \n");
            handleMatchDetailsMenu(match);
        }
    }

    // REQUIRES: match is not null
    // EFFECTS: Displays match details table
    private void handleMatchDetailsTable(Match match) {
        String overviewFormat = "| %-15s | %-9s | %-20s | %-5s | %-3s | %-4s |%n";
        ArrayList<Player> players = match.getPlayers();

        System.out.format("+-----------------+-----------+----------------------+-------+-----+------+%n");
        System.out.format("| Map             | Score     | Players              | K/D   | ADR | MVPs |%n");
        System.out.format("+-----------------+-----------+----------------------+-------+-----+------+%n");
        for (int i = 0; i < players.size(); i++) {
            try {
                MatchPerformance matchPerformance = players.get(i).getMatchStatsById(match.getMatchId());

                System.out.format(overviewFormat,
                        i == 0 ? match.getMap() : "",
                        i == 0 ? match.getRoundsWon() + " - " + match.getRoundsLost() : "",
                        players.get(i).getUsername(),
                        matchPerformance.getKD(),
                        matchPerformance.getTotalDamageDealt() / match.getTotalRounds(),
                        matchPerformance.getMostValuablePlayerAwards());
            } catch (MatchNotFoundException e) {
                System.out.format(overviewFormat,
                        i == 0 ? match.getMap() : "",
                        i == 0 ? match.getRoundsWon() + " - " + match.getRoundsLost() : "",
                        players.get(i).getUsername(), "N/A", "N/A", "N/A");
            }
        }
        System.out.format("+-----------------+-----------+----------------------+-------+-----+------+%n%n");
    }

    // EFFECTS: Displays add match interface
    private void displayMatchAddMenu() {
        System.out.println("Enter the roster id: (Type \"cancel\" to return to main menu) \n");
        String rosterId = scanner.nextLine();

        if (rosterId.equals("cancel")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        try {
            Roster roster = appData.getRosterById(rosterId);

            System.out.println("\nEnter the number of rounds won: \n");
            int wonRounds = Integer.parseInt(scanner.nextLine());

            System.out.println("\nEnter the number of rounds lost: \n");
            int lostRounds = Integer.parseInt(scanner.nextLine());

            System.out.println("\nEnter the map: \n");
            String map = scanner.nextLine();

            appData.addMatch(roster, wonRounds, lostRounds, map);

            System.out.println("\nMatch added successfully. To add player stats, go to the \"Edit Match\" menu.");
            System.out.println("Returning to main menu. \n");
            displayMainMenu();
        } catch (RosterNotFoundException e) {
            System.out.println("\nNo roster with that id exists. Try again. \n");
            displayMatchAddMenu();
        }
    }

    // EFFECTS: Displays match edit menu
    private void displayMatchEditMenu() {
        System.out.println("Enter the match ID: (Type \"cancel\" to return to matches overview) \n");
        String matchId = scanner.nextLine();

        if (matchId.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to matches overview. \n");
            displayMatchesOverviewMenu();
            return;
        }
        try {
            Match match = appData.getMatchById(Integer.parseInt(matchId));

            handleMatchEdit(match);
        } catch (MatchNotFoundException e) {
            System.out.println("\nNo match with that id exists. Try again. \n");
            displayMatchEditMenu();
        }
    }

    // EFFECTS: Handles match edit mode selection
    private void handleMatchEdit(Match match) {
        System.out.println("\nSelect an edit mode:");
        System.out.println("[1] Set player performance");
        System.out.println("[2] Back to matches overview\n");

        String mode = scanner.nextLine();

        if (mode.equals("1")) {
            handleMatchEditPlayerSelect(match);
        } else if (mode.equals("2")) {
            displayMatchesOverviewMenu();
        } else {
            System.out.println("\nInvalid selection, try again. \n");
            handleMatchEdit(match);
        }
    }

    // REQUIRES: match is not null
    // EFFECTS: Handles editing player performance for the consumed match
    private void handleMatchEditPlayerSelect(Match match) {
        System.out.println("\nEnter the player username: (Type \"cancel\" to return to matches overview) \n");
        String playerUsername = scanner.nextLine();

        if (playerUsername.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to matches overview. \n");
            displayMatchesOverviewMenu();
            return;
        }

        try {
            Player player = appData.getPlayerByUsername(playerUsername);
            handleMatchEditPlayerPerformance(match, player);
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with that id exists. Try again. \n");
            handleMatchEditPlayerSelect(match);
        }
    }

    // REQUIRES: match is not null, player is not null
    // EFFECTS: Handles editing performance data for a player in a match
    private void handleMatchEditPlayerPerformance(Match match, Player player) {
        System.out.println("\nEnter the total damage dealt: \n");
        int damage = Integer.parseInt(scanner.nextLine());

        System.out.println("\nEnter the number of game points gained: \n");
        int points = Integer.parseInt(scanner.nextLine());

        System.out.println("\nEnter the number of kills: \n");
        int kills = Integer.parseInt(scanner.nextLine());

        System.out.println("\nEnter the number of assists: \n");
        int assists = Integer.parseInt(scanner.nextLine());

        System.out.println("\nEnter the number of deaths: \n");
        int deaths = Integer.parseInt(scanner.nextLine());

        System.out.println("\nEnter the number of MVPs: \n");
        int mostValuablePlayerAwards = Integer.parseInt(scanner.nextLine());

        player.setMatchStats(match.getMatchId(), damage, points, kills, assists, deaths, mostValuablePlayerAwards);

        System.out.println("\nMatch details modified successfully.");
        System.out.println("Returning to matches overview. \n");
        displayMatchesOverviewMenu();
    }

    // EFFECTS: Displays players overview
    private void displayPlayersOverviewMenu() {
        ArrayList<Player> players = appData.getPlayers();

        if (players.isEmpty()) {
            System.out.println("No players have been added yet. \n");
        } else {
            String overviewFormat = "| %-15s | %-12d | %-13d | %-4d |%n";

            System.out.format("+-----------------+--------------+---------------+------+%n");
            System.out.format("| Player          | Games Played | Rounds Played | MVPs |%n");
            System.out.format("+-----------------+--------------+---------------+------+%n");
            for (Player player : players) {
                System.out.format(overviewFormat, player.getUsername(), player.getGamesPlayed(),
                        player.getRoundsPlayed(), player.getMostValuablePlayerAwards());
            }
            System.out.format("+-----------------+--------------+---------------+------+%n%n");
        }

        ArrayList<String> optionsText = new ArrayList<>();

        optionsText.add("[1] Add new player");
        optionsText.add("[2] Edit players");
        optionsText.add("[3] Back to main menu");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayPlayerAddMenu);
        commands.put("2", this::displayPlayerEditMenu);
        commands.put("3", this::displayMainMenu);

        handleMenu(optionsText, commands);
    }

    // EFFECTS: Displays player add menu
    private void displayPlayerAddMenu() {
        System.out.println("Enter the player's username: \n");
        String username = scanner.nextLine();

        appData.addPlayer(username);

        System.out.println("\nPlayer created successfully. To edit this player, go to the \"Edit players\" menu.");
        System.out.println("Returning to players overview. \n");
        displayPlayersOverviewMenu();
    }

    // EFFECTS: Displays player edit menu
    private void displayPlayerEditMenu() {
        System.out.println("Enter the player's username: (Type \"cancel\" to return to players overview) \n");
        String username = scanner.nextLine();

        if (username.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to players overview. \n");
            displayPlayersOverviewMenu();
            return;
        }

        try {
            Player player = appData.getPlayerByUsername(username);

            System.out.println("\nEnter the new username: (Type \"cancel\" to return to players overview) \n");

            String newUsername = scanner.nextLine();

            if (newUsername.equals("cancel")) {
                System.out.println("\nAction cancelled by user. Returning to players overview. \n");
                displayPlayersOverviewMenu();
                return;
            }

            player.setUsername(newUsername);

            System.out.println("\nPlayer edited successfully. Returning to players overview. \n");
            displayPlayersOverviewMenu();
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with that username exists. Try again. \n");
            displayPlayerEditMenu();
        }
    }

    // EFFECTS: Displays rosters overview
    private void displayRostersOverviewMenu() {
        ArrayList<Roster> rosters = appData.getRosters();

        if (rosters.isEmpty()) {
            System.out.println("No rosters have been added yet. \n");
        } else {
            displayRostersOverviewTable(rosters);
        }

        ArrayList<String> optionsText = new ArrayList<>();

        optionsText.add("[1] Add new roster");
        optionsText.add("[2] Edit rosters");
        optionsText.add("[3] Back to main menu");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayRosterAddMenu);
        commands.put("2", this::displayRosterEditMenu);
        commands.put("3", this::displayMainMenu);

        handleMenu(optionsText, commands);
    }

    // REQUIRES: rosters is not null
    // EFFECTS: Displays table of rosters with overview stats
    private void displayRostersOverviewTable(ArrayList<Roster> rosters) {
        String overviewFormat = "| %-15s | %-13s | %-20s |%n";

        System.out.format("+-----------------+---------------+----------------------+%n");
        System.out.format("| Roster          | Avg. Win Rate | Players on Roster    |%n");
        System.out.format("+-----------------+---------------+----------------------+%n");
        for (Roster roster : rosters) {
            ArrayList<Player> players = roster.getPlayers();
            for (int i = 0; i < players.size(); i++) {
                System.out.format(overviewFormat,
                        i == 0 ? roster.getId() : "",
                        i == 0 ? (roster.getWinRate() + "%") : "",
                        players.get(i).getUsername());
            }
            System.out.format("+-----------------+---------------+----------------------+%n");
        }
        System.out.println();
    }

    // EFFECTS: Displays add roster interface
    private void displayRosterAddMenu() {
        System.out.println("Enter a roster identifier (id): \n");
        String id = scanner.nextLine();

        System.out.println("\nThe usernames of the players in this roster, separated by commas: \n");
        String players = scanner.nextLine();

        String[] playerUsernames = players.split(",");

        ArrayList<Player> playersArrayList = new ArrayList<>();

        for (String username : playerUsernames) {
            try {
                Player player = appData.getPlayerByUsername(username.trim());
                playersArrayList.add(player);
            } catch (PlayerNotFoundException e) {
                System.out.println("\nNo player with the username " + username + " exists. Try again. \n");
                displayRosterAddMenu();
                return;
            }
        }

        appData.addRoster(id, playersArrayList);

        System.out.println("\nRoster created successfully. To edit this roster, go to the \"Edit rosters\" menu.");
        System.out.println("Returning to rosters overview. \n");
        displayRostersOverviewMenu();
    }

    // EFFECTS: Displays roster edit interface
    private void displayRosterEditMenu() {
        System.out.println("Enter the roster id: (Type \"cancel\" to return to rosters overview) \n");
        String rosterId = scanner.nextLine();

        if (rosterId.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to rosters overview. \n");
            displayRostersOverviewMenu();
            return;
        }

        try {
            Roster roster = appData.getRosterById(rosterId);

            handleRosterEditSelect(roster);
        } catch (RosterNotFoundException e) {
            System.out.println("\nNo roster with that id exists. Try again. \n");
            displayRosterEditMenu();
        }
    }

    // REQUIRES: roster is not null
    // EFFECTS: Handles second step of editing roster (Selecting edit mode)
    private void handleRosterEditSelect(Roster roster) {
        System.out.println("\nSelect an edit mode.");
        System.out.println("[1] Add player to roster");
        System.out.println("[2] Remove player from roster");
        System.out.println("[3] Delete roster");
        System.out.println("[4] Back to rosters overview");
        System.out.println();

        String select = scanner.nextLine();

        switch (select) {
            case "1":
                handleRosterEditAdd(roster);
            case "2":
                handleRosterEditRemove(roster);
            case "3":
                handleRosterEditDelete(roster);
            case "4":
                System.out.println("\nAction cancelled by user. Returning to rosters overview. \n");
                displayRostersOverviewMenu();
            default:
                System.out.println("\nInvalid input. Try again. \n");
                handleRosterEditSelect(roster);
        }
    }

    // REQUIRES: roster is not null
    // EFFECTS: Handles adding player to provided roster
    private void handleRosterEditAdd(Roster roster) {
        System.out.println("\nEnter the username of the player you want to add: \n");
        String username = scanner.nextLine();

        try {
            Player player = appData.getPlayerByUsername(username);
            roster.addPlayer(player);
            handleRosterEditComplete("Player added successfully.");
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with the username " + username + " exists. Try again. \n");
            handleRosterEditAdd(roster);
        }
    }

    // REQUIRES: roster is not null
    // EFFECTS: Handles removing player from provided roster
    private void handleRosterEditRemove(Roster roster) {
        System.out.println("\nEnter the username of the player you want to remove: \n");
        String username = scanner.nextLine();

        try {
            Player player = appData.getPlayerByUsername(username);
            roster.removePlayer(player);
            handleRosterEditComplete("Player removed successfully.");
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with the username " + username + " exists. Try again. \n");
            handleRosterEditRemove(roster);
        }
    }

    // REQUIRES: roster is not null
    // EFFECTS: Handles deleting roster
    private void handleRosterEditDelete(Roster roster) {
        System.out.println("\nConfirm deletion - enter roster id: (Type \"cancel\" to return to rosters overview) \n");
        String confirmation = scanner.nextLine();

        if (confirmation.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to rosters overview. \n");
            displayRostersOverviewMenu();
        } else if (confirmation.equals(roster.getId())) {
            appData.deleteRoster(roster);
            handleRosterEditComplete("Roster deleted successfully.");
        } else {
            System.out.println("\nInvalid input. Try again. \n");
            handleRosterEditDelete(roster);
        }
    }

    // EFFECTS: Handles completion of roster edit
    private void handleRosterEditComplete(String prefix) {
        System.out.println("\n" + prefix.trim() + " Returning to main menu. \n");
        displayRostersOverviewMenu();
    }

    // EFFECTS: Displays exit message and exits program
    private void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
