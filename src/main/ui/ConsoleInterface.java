package ui;

import java.util.*;

import model.Player;
import model.Roster;
import model.Match;

public class ConsoleInterface {
    private String currentMenu;
    private ArrayList<Player> players;
    private ArrayList<Roster> rosters;
    private ArrayList<Match> matches;

    // EFFECTS: Initializes app, displays welcome message and displays application main menu
    public ConsoleInterface() {
        currentMenu = "";
        players = new ArrayList<>();
        rosters = new ArrayList<>();
        matches = new ArrayList<>();

        System.out.println("Welcome to Track:GO!\n");
        displayMainMenu();
    }

    // EFFECTS: Generalized menu handler, handles user selection and displays appropriate menu
    private void handleMenu(Map<String, Runnable> commands) {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
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
            System.out.println("[1] Add new match");
            System.out.println("[2] Back to main menu");
        } else {
            for (Match match : matches) {
                System.out.println(match.getOverview());
            }

            System.out.println("[1] Add new match");
            System.out.println("[2] Back to main menu");
        }

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("1", this::displayAddMatchMenu);
        commands.put("2", this::displayMainMenu);

        handleMenu(commands);
    }

    private void displayAddMatchMenu() {
        currentMenu = "add_matches";
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
        Scanner scanner = new Scanner(System.in);
        String select = scanner.nextLine();
        System.out.println();
        return select.equals("y");
    }

    private boolean userSelectsNo() {
        Scanner scanner = new Scanner(System.in);
        String select = scanner.nextLine();
        System.out.println();
        return select.equals("n");
    }
}
