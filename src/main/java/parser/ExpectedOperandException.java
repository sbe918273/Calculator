package parser;

/**
 * An exception to throw if a parser expected but did not receive an operand token.
 */
public class ExpectedOperandException extends IllegalTokenException {

    /**
     * Initialises the names of the class and method that threw this exception, the input line and number when the
     * exception was thrown and the exception's description. We initialise the resulting exception's message to be
     * "[`className`:`methodName` `lineNumber`:`characterNumber`] `description`".
     * @param className the name of the class that threw this exception
     * @param methodName the name of the method that threw this exception
     * @param lineNumber the input line number when this exception was thrown
     * @param characterNumber the input character number when this exception was thrown
     * @param description this exception's description
     */
    public ExpectedOperandException(
            String className,
            String methodName,
            int lineNumber,
            int characterNumber,
            String description
    ) {
        super(className, methodName, lineNumber, characterNumber, description);
    }
}
