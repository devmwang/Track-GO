package ui;

import java.util.*;

import model.*;

public class ConsoleInterface {
    private final Scanner scanner;
    private final AppData appData;

    // EFFECTS: Initializes app, displays welcome message and displays application main menu
    public ConsoleInterface() {
        scanner = new Scanner(System.in);
        appData = new AppData();

        System.out.println("Welcome to Track:GO!\n");
        displayMainMenu();
    }

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
        optionsText.add("[5] Exit");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayMatchesMenu);
        commands.put("2", this::displayAddMatchMenu);
        commands.put("3", this::displayPlayersMenu);
        commands.put("4", this::displayRostersMenu);
        commands.put("5", this::exit);

        handleMenu(optionsText, commands);
    }

    // EFFECTS: Displays matches overview
    private void displayMatchesMenu() {
        ArrayList<Match> matches = appData.getMatches();

        if (matches.isEmpty()) {
            System.out.println("No matches have been added yet. \n");
        } else {
            for (Match match : matches) {
                System.out.println(match.getOverview());
            }
        }

        ArrayList<String> optionsText = new ArrayList<>();

        optionsText.add("[1] Add new match");
        optionsText.add("[2] Back to main menu");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayAddMatchMenu);
        commands.put("2", this::displayMainMenu);

        handleMenu(optionsText, commands);
    }

    // EFFECTS: Displays add match interface
    private void displayAddMatchMenu() {
        System.out.println("Note that to complete this action, you must have the associated roster id.");
        System.out.println("Do you want to proceed? [y/n] \n");

        String input = scanner.nextLine();

        if (input.equals("n")) {
            System.out.println("\nAction cancelled by user. Returning to main menu. \n");
            displayMainMenu();
        } else if (input.equals("y")) {
            handleAddMatch();
        } else {
            System.out.println("\nInvalid selection, Try again. \n");
            displayAddMatchMenu();
        }
    }

    // EFFECTS: Handles adding match to appData
    private void handleAddMatch() {
        System.out.println("\nEnter the roster id: (Type \"cancel\" to return to main menu) \n");
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

            appData.addMatch(roster, wonRounds, lostRounds);

            System.out.println("Match added successfully. To add player stats, go to the \"Edit Match\" menu.");
            System.out.println("Returning to main menu. \n");
            displayMainMenu();
        } catch (RosterNotFoundException e) {
            System.out.println("\nNo roster with that id exists. Try again. \n");
            handleAddMatch();
        }
    }

    // EFFECTS: Displays players overview
    private void displayPlayersMenu() {
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

        commands.put("1", this::displayAddPlayerMenu);
        commands.put("2", this::displayEditPlayersMenu);
        commands.put("3", this::displayMainMenu);

        handleMenu(optionsText, commands);
    }

    private void displayAddPlayerMenu() {
        System.out.println("Enter the player's username: \n");
        String username = scanner.nextLine();

        appData.addPlayer(username);

        System.out.println("\nPlayer created successfully. To edit this player, go to the \"Edit players\" menu.");
        System.out.println("Returning to players overview. \n");
        displayPlayersMenu();
    }

    private void displayEditPlayersMenu() {
        System.out.println("Note that to complete this action, you must have the player's username.");
        System.out.println("Do you want to proceed? [y/n] \n");

        String input = scanner.nextLine();

        if (input.equals("n")) {
            System.out.println("\nAction cancelled by user. Returning to main menu. \n");
            displayMainMenu();
        } else if (input.equals("y")) {
            handleEditPlayer();
        } else {
            System.out.println("\nInvalid input. Try again. \n");
            displayEditPlayersMenu();
        }
    }

    private void handleEditPlayer() {
        System.out.println("\nEnter the player's username: (Type \"cancel\" to return to main menu) \n");
        String username = scanner.nextLine();

        if (username.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        try {
            Player player = appData.getPlayerByUsername(username);

            System.out.println("\nEnter the new username: (Type \"cancel\" to return to main menu) \n");

            String newUsername = scanner.nextLine();

            if (newUsername.equals("cancel")) {
                System.out.println("\nAction cancelled by user. Returning to main menu. \n");
                displayMainMenu();
                return;
            }

            player.setUsername(newUsername);

            System.out.println("\nPlayer edited successfully. Returning to main menu. \n");
            displayMainMenu();
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with that username exists. Try again. \n");
            handleEditPlayer();
        }
    }

    // EFFECTS: Displays rosters overview
    private void displayRostersMenu() {
        ArrayList<Roster> rosters = appData.getRosters();

        if (rosters.isEmpty()) {
            System.out.println("No rosters have been added yet. \n");
        } else {
            String overviewFormat = "| %-15s | %-13s |%n";

            System.out.format("+-----------------+---------------+%n");
            System.out.format("| Roster          | Avg. Win Rate |%n");
            System.out.format("+-----------------+---------------+%n");
            for (Roster roster : rosters) {
                System.out.format(overviewFormat, roster.getId(), roster.getWinRate());
            }
            System.out.format("+-----------------+---------------+%n%n");
        }

        ArrayList<String> optionsText = new ArrayList<>();

        optionsText.add("[1] Add new roster");
        optionsText.add("[2] Edit rosters");
        optionsText.add("[3] Back to main menu");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayAddRosterMenu);
        commands.put("2", this::displayEditRostersMenu);
        commands.put("3", this::displayMainMenu);

        handleMenu(optionsText, commands);
    }

    // EFFECTS: Displays add roster interface
    private void displayAddRosterMenu() {
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
                displayAddRosterMenu();
                return;
            }
        }

        appData.addRoster(id, playersArrayList);

        System.out.println("\nRoster created successfully. To edit this roster, go to the \"Edit rosters\" menu.");
        System.out.println("Returning to rosters overview. \n");
        displayRostersMenu();
    }

    // EFFECTS: Displays roster edit interface
    private void displayEditRostersMenu() {
        System.out.println("Note that to complete this action, you must have the associated roster id.");
        System.out.println("Do you want to proceed? [y/n] \n");

        String input = scanner.nextLine();

        if (input.equals("n")) {
            System.out.println("\nAction cancelled by user. Returning to main menu. \n");
            displayMainMenu();
        } else if (input.equals("y")) {
            handleEditRoster();
        } else {
            System.out.println("\nInvalid input. Try again. \n");
            displayEditRostersMenu();
        }
    }

    // EFFECTS: Handles first step of editing roster
    private void handleEditRoster() {
        System.out.println("\nEnter the roster id: (Type \"cancel\" to return to main menu) \n");
        String rosterId = scanner.nextLine();

        if (rosterId.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        try {
            Roster roster = appData.getRosterById(rosterId);

            handleEditRosterSelect(roster);
        } catch (RosterNotFoundException e) {
            System.out.println("\nNo roster with that id exists. Try again. \n");
            handleEditRoster();
        }
    }

    // REQUIRES: roster in appData
    // EFFECTS: Handles second step of editing roster (Selecting edit mode)
    private void handleEditRosterSelect(Roster roster) {
        System.out.println("\nSelect an edit mode.");
        System.out.println("[1] Add player to roster");
        System.out.println("[2] Remove player from roster");
        System.out.println("[3] Delete roster");
        System.out.println("[4] Back to main menu");

        String select = scanner.nextLine();

        switch (select) {
            case "1":
                handleEditRosterAdd(roster);
            case "2":
                handleEditRosterRemove(roster);
            case "3":
                handleEditRosterDelete(roster);
            case "4":
                System.out.println("\nAction cancelled by user. Returning to main menu. \n");
                displayMainMenu();
            default:
                System.out.println("\nInvalid input. Try again. \n");
                handleEditRosterSelect(roster);
        }
    }

    // REQUIRES: roster in appData
    // EFFECTS: Handles adding player to provided roster
    private void handleEditRosterAdd(Roster roster) {
        System.out.println("\nEnter the username of the player you want to add: \n");
        String username = scanner.nextLine();

        try {
            Player player = appData.getPlayerByUsername(username);
            roster.addPlayer(player);
            handleRosterEditComplete("Player added successfully.");
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with the username " + username + " exists. Try again. \n");
            handleEditRosterAdd(roster);
        }
    }

    // REQUIRES: roster in appData
    // EFFECTS: Handles removing player from provided roster
    private void handleEditRosterRemove(Roster roster) {
        System.out.println("\nEnter the username of the player you want to remove: \n");
        String username = scanner.nextLine();

        try {
            Player player = appData.getPlayerByUsername(username);
            roster.removePlayer(player);
            handleRosterEditComplete("Player removed successfully.");
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with the username " + username + " exists. Try again. \n");
            handleEditRosterRemove(roster);
        }
    }

    // REQUIRES: roster in appData
    // EFFECTS: Handles deleting roster
    private void handleEditRosterDelete(Roster roster) {
        System.out.println("\nConfirm deletion by typing in roster id: (Type \"cancel\" to return to main menu) \n");
        String confirmation = scanner.nextLine();

        if (confirmation.equals("cancel")) {
            System.out.println("\nAction cancelled by user. Returning to main menu. \n");
            displayMainMenu();
        } else if (confirmation.equals(roster.getId())) {
            appData.deleteRoster(roster);
            handleRosterEditComplete("Roster deleted successfully.");
        } else {
            System.out.println("\nInvalid input. Try again. \n");
            handleEditRosterDelete(roster);
        }
    }

    private void handleRosterEditComplete(String prefix) {
        System.out.println("\n" + prefix.trim() + " Returning to main menu. \n");
        displayRostersMenu();
    }

    // EFFECTS: Displays exit message and exits program
    private void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
