package model;

public class PlayerNotFoundException extends Exception {
    // EFFECTS: Constructs a new PlayerNotFoundException with no detail message
    public PlayerNotFoundException() {
        super();
    }

    // REQUIRES: message is not null
    // EFFECTS: Constructs a new PlayerNotFoundException with a detail message
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
