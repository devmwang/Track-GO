package ui;

import java.util.*;

import model.*;

public class ConsoleInterface {
    private String currentMenu;
    private Scanner scanner;
    private AppData appData;

    // EFFECTS: Initializes app, displays welcome message and displays application main menu
    public ConsoleInterface() {
        currentMenu = "";
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
        currentMenu = "main";
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

    private void displayMatchesMenu() {
        currentMenu = "view_matches";

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

    private void displayAddMatchMenu() {
        currentMenu = "add_matches";

        System.out.println("Note that to complete this action, you must have the associated roster id.");
        System.out.println("Do you want to proceed? [y/n] \n");
        if (userSelects("n")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }
        handleAddMatch();
    }

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

    private void displayPlayersMenu() {
        currentMenu = "view_players";
    }

    private void displayRosterMenu() {
        currentMenu = "view_rosters";

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

    private void displayAddRosterMenu() {
        currentMenu = "add_roster";

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

    private void displayEditRostersMenu() {
        currentMenu = "edit_rosters";

        System.out.println("Note that to complete this action, you must have the associated roster id.");
        System.out.println("Do you want to proceed? [y/n] \n");
        if (userSelects("n")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        handleEditRoster();
    }

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

//            System.out.println("Match added successfully.");
//            System.out.println("Returning to main menu. \n");
//            displayMainMenu();
        } catch (RosterNotFoundException e) {
            System.out.println("\nNo roster with that id exists. Please try again. \n");
            handleEditRoster();
        }
    }

    private void handleEditRosterSelect(Roster roster) {
        System.out.println("\nDo you want to add or remove a player? [add/remove] \n");

        if (userSelects("add")) {
            handleEditRosterAdd(roster);
        } else if (userSelects("remove")) {
            handleEditRosterRemove(roster);
        } else {
            System.out.println("Invalid input. Please try again. \n");
            handleEditRosterSelect(roster);
        }
    }

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

    private void exit() {
        currentMenu = "exit";
        System.out.println("Goodbye!");
        System.exit(0);
    }

    private boolean userSelects(String string) {
        String select = scanner.nextLine();
        System.out.println();
        return select.equals(string);
    }
}
