package parser;

import main.PositionedException;

/**
 * An exception to throw iff the lexer encounters an invalid token (a token that does not allow correct parsing).
 */
public class IllegalTokenException extends PositionedException {
    /**
     * Initialises the names of the class and method that threw this exception, the input line and number when the
     * exception was thrown and the exception's description. We initialise the resulting exception's message to be
     * "[`className`:`methodName` `lineNumber`:`characterNumber`] `description`".
     * @param className the name of the class that threw this exception
     * @param methodName the name of the method that threw this exception
     * @param description this exception's description
     */
    public IllegalTokenException(
            String className,
            String methodName,
            int lineNumber,
            int characterNumber,
            String description
    ) {
        super(className, methodName, lineNumber, characterNumber, description);
    }
}
