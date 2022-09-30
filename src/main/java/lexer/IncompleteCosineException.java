package lexer;

/**
 * An exception to throw iff the lexer encounters an unfinished cosine token.
 */
public class IncompleteCosineException extends IllegalLexemeException {
    public IncompleteCosineException(
            String className,
            String methodName,
            int lineNumber,
            int characterNumber,
            String description
    ) {
        super(className, methodName, lineNumber, characterNumber, description);
    }
}
