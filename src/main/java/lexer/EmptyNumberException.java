package lexer;

/**
 * An exception to throw iff the lexer encounters a number that is solely (barre the exponent) a decimal point.
 */
public class EmptyNumberException extends MissingIntegerException {
    public EmptyNumberException(String message) {
        super(message);
    }
}
