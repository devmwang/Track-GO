package ui;

import java.util.Scanner;

public class ConsoleInterface {
    public ConsoleInterface() {
        System.out.println("Welcome to Track:GO!");
        displayMainMenu();
    }

    private void displayMainMenu() {
        System.out.println("Please select an option:");

        System.out.println("[1] View matches");
        System.out.println("[2] Add new match");
        System.out.println("[3] View players");
        System.out.println("[4] View roster");
        System.out.println("[5] Exit");

        Scanner scanner = new Scanner(System.in);

        String select = scanner.nextLine();

        System.out.println();

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
                System.out.println("Invalid selection, please try again.");
                System.out.println();
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

    public static void main(String[] args) {
        new ConsoleInterface();
    }
}
