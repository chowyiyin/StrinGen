package stringen.ui.exceptions;

/**
 * Represents an invalid input error by the user encountered during response collection.
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

}
