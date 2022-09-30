package lexer;

/**
 * An exception to throw iff the lexer encounters an illegal character that is a prefix to no lexemes.
 */
public class IllegalCharacterException extends IllegalLexemeException {
    public IllegalCharacterException(
            String className,
            String methodName,
            int lineNumber,
            int characterNumber,
            String description
    ) {
        super(className, methodName, lineNumber, characterNumber, description);
    }
}
