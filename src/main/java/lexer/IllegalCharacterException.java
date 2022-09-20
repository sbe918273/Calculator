package lexer;

/**
 * An exception to throw iff the lexer encounters an illegal character that is a prefix to no lexemes.
 */
public class IllegalCharacterException extends InvalidTokenException {
    public IllegalCharacterException(String message) {
        super(message);
    }
}
