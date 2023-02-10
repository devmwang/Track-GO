package model;

public class RosterNotFoundException extends Exception {
    public RosterNotFoundException() {
        super();
    }

    public RosterNotFoundException(String message) {
        super(message);
    }
}
