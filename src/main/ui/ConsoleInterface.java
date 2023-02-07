package ui;

import java.util.*;

import model.Player;
import model.Roster;
import model.Match;

public class ConsoleInterface {
    private String currentMenu;
    private Scanner scanner;
    private ArrayList<Player> players;
    private ArrayList<Roster> rosters;
    private ArrayList<Match> matches;

    // EFFECTS: Initializes app, displays welcome message and displays application main menu
    public ConsoleInterface() {
        currentMenu = "";
        scanner = new Scanner(System.in);
        players = new ArrayList<>();
        rosters = new ArrayList<>();
        matches = new ArrayList<>();

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
        System.out.println("[4] View roster");
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
        if (userSelectsNo()) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }
        handleAddMatch();
    }

    private void handleAddMatch() {
        System.out.println("Please enter the roster id: (Type \"cancel\" to return to main menu) \n");
        String rosterId = scanner.nextLine();
        Roster roster = null;

        if (rosterId.equals("cancel")) {
            System.out.println("Action cancelled by user. Returning to main menu. \n");
            displayMainMenu();
            return;
        }

        for (Roster r : rosters) {
            if (r.getId().equals(rosterId)) {
                roster = r;
                break;
            }
        }

        if (roster == null) {
            System.out.println("\nNo roster with that id exists. Please try again. \n");
            handleAddMatch();
            return;
        }

        System.out.println("\nPlease enter the number of rounds won: \n");
        int wonRounds = Integer.parseInt(scanner.nextLine());

        System.out.println("\nPlease enter the number of rounds lost: \n");
        int lostRounds = Integer.parseInt(scanner.nextLine());

        matches.add(new Match(roster, wonRounds, lostRounds));

        System.out.println("Match added successfully. Returning to main menu. \n");
        displayMainMenu();
    }

    private void displayPlayersMenu() {
        currentMenu = "view_players";
    }

    private void displayRosterMenu() {
        currentMenu = "view_rosters";
    }

    private void exit() {
        currentMenu = "exit";
        System.out.println("Goodbye!");
        System.exit(0);
    }

    private boolean userSelectsYes() {
        String select = scanner.nextLine();
        System.out.println();
        return select.equals("y");
    }

    private boolean userSelectsNo() {
        String select = scanner.nextLine();
        System.out.println();
        return select.equals("n");
    }
}
