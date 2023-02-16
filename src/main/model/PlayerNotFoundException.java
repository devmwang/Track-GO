package model;

// Represents an exception that is thrown when a roster is not found
public class PlayerNotFoundException extends Exception {
    // EFFECTS: Constructs a new PlayerNotFoundException with no detail message
    public PlayerNotFoundException() {
        super();
    }
}
