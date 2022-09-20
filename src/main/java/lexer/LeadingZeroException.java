package lexer;

/**
 * An exception to throw iff the lexer encounters a leading zero.
 */
public class LeadingZeroException extends InvalidTokenException {
    public LeadingZeroException(String message) {
        super(message);
    }
}
