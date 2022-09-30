package lexer;

/**
 * An exception to throw if the lexer does not encounter an expected integer.
 */
public class MissingIntegerException extends IllegalLexemeException {
    public MissingIntegerException(
            String className,
            String methodName,
            int lineNumber,
            int characterNumber,
            String description
    ) {
        super(className, methodName, lineNumber, characterNumber, description);
    }
}
