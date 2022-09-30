package lexer;

/**
 * An exception to throw iff the lexer encounters a leading zero.
 */
public class LeadingZeroException extends IllegalLexemeException {
    public LeadingZeroException(
            String className,
            String methodName,
            int lineNumber,
            int characterNumber,
            String description
    ) {
        super(className, methodName, lineNumber, characterNumber, description);
    }
}
