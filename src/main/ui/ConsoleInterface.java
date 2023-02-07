package ui;

import java.util.Scanner;

public class ConsoleInterface {
    // EFFECTS: Displays welcome message and displays application main menu
    public ConsoleInterface() {
        System.out.println("Welcome to Track:GO!\n");
        displayMainMenu();
    }

    // EFFECTS: Displays menu options and passes user selection to handleMenu
    private void displayMainMenu() {
        System.out.println("Please select an option:");

        System.out.println("[1] View matches");
        System.out.println("[2] Add new match");
        System.out.println("[3] View players");
        System.out.println("[4] View roster");
        System.out.println("[5] Exit \n");

        handleMainMenu();
    }

    // EFFECTS: Handles user selection and displays appropriate menu, quits, or displays main menu if invalid
    private void handleMainMenu() {
        Scanner scanner = new Scanner(System.in);

        String select = scanner.nextLine();

        switch (select) {
            case "1":
                displayMatchesMenu();
                break;
            case "2":
                displayAddMatchMenu();
                break;
            case "3":
                displayPlayersMenu();
                break;
            case "4":
                displayRosterMenu();
                break;
            case "5":
                break;
            default:
                System.out.println("Invalid selection, please try again. \n");
                displayMainMenu();
                break;
        }
    }

    private void displayMatchesMenu() {

    }

    private void displayAddMatchMenu() {

    }

    private void displayPlayersMenu() {

    }

    private void displayRosterMenu() {

    }
}
