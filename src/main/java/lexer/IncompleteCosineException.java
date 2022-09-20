package lexer;

/**
 * An exception to throw iff the lexer encounters an unfinished cosine token.
 */
public class IncompleteCosineException extends InvalidTokenException {
    public IncompleteCosineException(String message) {
        super(message);
    }
}
