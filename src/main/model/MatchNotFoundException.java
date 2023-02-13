package model;

public class MatchNotFoundException extends Exception {
    // EFFECTS: Constructs a new MatchNotFoundException with no detail message
    public MatchNotFoundException() {
        super();
    }

    // REQUIRES: message is not null
    // EFFECTS: Constructs a new MatchNotFoundException with a detail message
    public MatchNotFoundException(String message) {
        super(message);
    }
}
