package exceptions;

// Represents an exception that is thrown when consumed app data is invalid
public class AppDataInvalidException extends Exception {
    // EFFECTS: Constructs a new AppDataInvalidException with no detail message
    public AppDataInvalidException() {
        super();
    }
}
