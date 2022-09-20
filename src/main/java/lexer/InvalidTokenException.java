package lexer;

/**
 * An exception to throw iff the lexer encounters an invalid token (a string that it cannot match to a token).
 */
public class InvalidTokenException extends Exception {
    public InvalidTokenException(String message) {
        super(message);
    }
}
