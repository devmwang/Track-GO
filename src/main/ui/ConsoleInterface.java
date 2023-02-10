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
    private void handleMenu(Map<String, Runnable> commands) {
        System.out.println();
        String select = scanner.nextLine();
        System.out.println();

        if (commands.containsKey(select)) {
            commands.get(select).run();
        } else {
            System.out.println("Invalid selection, please try again. \n");
            handleMenu(commands);
        }
    }

    // EFFECTS: Displays menu options and passes user selection to handleMenu
    private void displayMainMenu() {
        System.out.println("Please select an option:");

        System.out.println("[1] View matches");
        System.out.println("[2] Add new match");
        System.out.println("[3] View players");
        System.out.println("[4] View rosters");
        System.out.println("[5] Exit");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayMatchesMenu);
        commands.put("2", this::displayAddMatchMenu);
        commands.put("3", this::displayPlayersMenu);
        commands.put("4", this::displayRosterMenu);
        commands.put("5", this::exit);

        handleMenu(commands);
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

        System.out.println("[1] Add new match");
        System.out.println("[2] Back to main menu");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayAddMatchMenu);
        commands.put("2", this::displayMainMenu);

        handleMenu(commands);
    }

    // EFFECTS: Displays add match interface
    private void displayAddMatchMenu() {
        System.out.println("Note that to complete this action, you must have the associated roster id.");
        System.out.println("Do you want to proceed? [y/n] \n");
        if (userSelects("n")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }
        handleAddMatch();
    }

    // EFFECTS: Handles adding match to appData
    private void handleAddMatch() {
        System.out.println("Please enter the roster id: (Type \"cancel\" to return to main menu) \n");
        String rosterId = scanner.nextLine();

        if (rosterId.equals("cancel")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        try {
            Roster roster = appData.getRosterById(rosterId);

            System.out.println("\nPlease enter the number of rounds won: \n");
            int wonRounds = Integer.parseInt(scanner.nextLine());

            System.out.println("\nPlease enter the number of rounds lost: \n");
            int lostRounds = Integer.parseInt(scanner.nextLine());

            appData.addMatch(roster, wonRounds, lostRounds);

            System.out.println("Match added successfully. To add player stats, please go to the \"Edit Match\" menu.");
            System.out.println("Returning to main menu. \n");
            displayMainMenu();
        } catch (RosterNotFoundException e) {
            System.out.println("\nNo roster with that id exists. Please try again. \n");
            handleAddMatch();
        }
    }

    // EFFECTS: Displays players overview
    private void displayPlayersMenu() {
        ArrayList<Player> players = appData.getPlayers();

        if (players.isEmpty()) {
            System.out.println("No players have been added yet. \n");
        } else {
            System.out.println("------------------------------------------------------------");
            for (Player player : players) {
                System.out.println(player.getOverview());
                System.out.println("------------------------------------------------------------");
            }
        }

        System.out.println("[1] Add new player");
        System.out.println("[2] Edit players");
        System.out.println("[3] Back to main menu");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayAddPlayerMenu);
        commands.put("2", this::displayEditPlayersMenu);
        commands.put("3", this::displayMainMenu);

        handleMenu(commands);
    }

    private void displayAddPlayerMenu() {
        System.out.println("\nPlease enter the player's username: \n");
        String username = scanner.nextLine();

        appData.addPlayer(username);

        System.out.println("Player created successfully. To edit this player, please go to the \"Edit players\" menu.");
        System.out.println("Returning to main menu. \n");
        displayMainMenu();
    }

    private void displayEditPlayersMenu() {
        System.out.println("Note that to complete this action, you must have the player's username.");
        System.out.println("Do you want to proceed? [y/n] \n");
        if (userSelects("n")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        handleEditPlayer();
    }

    private void handleEditPlayer() {
        System.out.println("Please enter the player's username: (Type \"cancel\" to return to main menu) \n");
        String username = scanner.nextLine();

        if (username.equals("cancel")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        try {
            Player player = appData.getPlayerByUsername(username);

            System.out.println("Enter the new username: (Type \"cancel\" to return to main menu) \n");

            String newUsername = scanner.nextLine();

            if (newUsername.equals("cancel")) {
                System.out.println("Action cancelled by user. Returning to main menu. \n");
                displayMainMenu();
                return;
            }

            player.setUsername(newUsername);
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with that username exists. Please try again. \n");
            handleEditPlayer();
        }
    }

    // EFFECTS: Displays rosters overview
    private void displayRosterMenu() {
        ArrayList<Roster> rosters = appData.getRosters();

        if (rosters.isEmpty()) {
            System.out.println("No rosters have been added yet. \n");
        } else {
            for (Roster roster : rosters) {
                System.out.println(roster.getOverview());
            }
        }

        System.out.println("[1] Add new roster");
        System.out.println("[2] Edit rosters");
        System.out.println("[3] Back to main menu");

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayAddRosterMenu);
        commands.put("2", this::displayEditRostersMenu);
        commands.put("3", this::displayMainMenu);

        handleMenu(commands);
    }

    // EFFECTS: Displays add roster interface
    private void displayAddRosterMenu() {
        System.out.println("\nPlease enter a roster identifier (id): \n");
        String id = scanner.nextLine();

        System.out.println("\nPlease enter the usernames of the players in this roster, separated by commas: \n");
        String players = scanner.nextLine();

        String[] playerUsernames = players.split(",");

        ArrayList<Player> playersArrayList = new ArrayList<>();

        for (String username : playerUsernames) {
            try {
                Player player = appData.getPlayerByUsername(username);
                playersArrayList.add(player);
            } catch (PlayerNotFoundException e) {
                System.out.println("\nNo player with the username " + username + " exists. Please try again. \n");
                displayAddRosterMenu();
                return;
            }
        }

        appData.addRoster(id, playersArrayList);

        System.out.println("Roster created successfully. To edit this roster, please go to the \"Edit rosters\" menu.");
        System.out.println("Returning to main menu. \n");
        displayMainMenu();
    }

    // EFFECTS: Displays roster edit interface
    private void displayEditRostersMenu() {
        System.out.println("Note that to complete this action, you must have the associated roster id.");
        System.out.println("Do you want to proceed? [y/n] \n");
        if (userSelects("n")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        handleEditRoster();
    }

    // EFFECTS: Handles first step of editing roster
    private void handleEditRoster() {
        System.out.println("Please enter the roster id: (Type \"cancel\" to return to main menu) \n");
        String rosterId = scanner.nextLine();

        if (rosterId.equals("cancel")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        try {
            Roster roster = appData.getRosterById(rosterId);

            handleEditRosterSelect(roster);
        } catch (RosterNotFoundException e) {
            System.out.println("\nNo roster with that id exists. Please try again. \n");
            handleEditRoster();
        }
    }

    // REQUIRES: roster in appData
    // EFFECTS: Handles second step of editing roster (Selecting edit mode)
    private void handleEditRosterSelect(Roster roster) {
        System.out.println("\nPlease select an edit mode. \n");
        System.out.println("[add] Add player to roster");
        System.out.println("[remove] Remove player from roster");
        System.out.println("[delete] Delete roster");
        System.out.println("[cancel] Back to main menu");

        String select = scanner.nextLine();

        switch (select) {
            case "add":
                handleEditRosterAdd(roster);
            case "remove":
                handleEditRosterRemove(roster);
            case "delete":
                handleEditRosterDelete(roster);
            case "cancel":
                System.out.println("Action cancelled by user. Returning to main menu. \n");
                displayMainMenu();
            default:
                System.out.println("Invalid input. Please try again. \n");
                handleEditRosterSelect(roster);
        }
    }

    // REQUIRES: roster in appData
    // EFFECTS: Handles adding player to provided roster
    private void handleEditRosterAdd(Roster roster) {
        System.out.println("\nPlease enter the username of the player you want to add: \n");
        String username = scanner.nextLine();

        try {
            Player player = appData.getPlayerByUsername(username);
            roster.addPlayer(player);
            System.out.println("Player added successfully. Returning to main menu. \n");
            displayMainMenu();
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with the username " + username + " exists. Please try again. \n");
            handleEditRosterAdd(roster);
        }
    }

    // REQUIRES: roster in appData
    // EFFECTS: Handles removing player from provided roster
    private void handleEditRosterRemove(Roster roster) {
        System.out.println("\nPlease enter the username of the player you want to remove: \n");
        String username = scanner.nextLine();

        try {
            Player player = appData.getPlayerByUsername(username);
            roster.removePlayer(player);
            System.out.println("Player removed successfully. Returning to main menu. \n");
            displayMainMenu();
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo player with the username " + username + " exists. Please try again. \n");
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
            System.out.println("\nRoster deleted successfully. Returning to main menu. \n");
            displayMainMenu();
        } else {
            System.out.println("\nInvalid input. Please try again. \n");
            handleEditRosterDelete(roster);
        }
    }

    // EFFECTS: Displays exit message and exits program
    private void exit() {
        System.out.println("\nGoodbye!");
        System.exit(0);
    }

    // REQUIRES: string
    // EFFECTS: Checks if user input matches provided string and return result
    private boolean userSelects(String string) {
        String select = scanner.nextLine();
        System.out.println();
        return select.equals(string);
    }
}
