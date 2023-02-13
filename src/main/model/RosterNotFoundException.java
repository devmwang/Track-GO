package model;

public class RosterNotFoundException extends Exception {
    // EFFECTS: Constructs a new RosterNotFoundException with no detail message
    public RosterNotFoundException() {
        super();
    }

    // REQUIRES: message is not null
    // EFFECTS: Constructs a new RosterNotFoundException with a detail message
    public RosterNotFoundException(String message) {
        super(message);
    }
}
