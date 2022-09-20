package lexer;

/**
 * An exception to throw if the lexer does not encounter an expected integer.
 */
public class MissingIntegerException extends RuntimeException {
    public MissingIntegerException(String message) {
        super(message);
    }
}
