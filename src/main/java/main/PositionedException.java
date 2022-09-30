package main;

/**
 * A checked exception that has attributes of the names of the class and method that threw this exception, the input
 * line and number when the exception was thrown and the exception's description.
 */
public class PositionedException extends Exception {

    private final String className;
    private final String methodName;
    private final int lineNumber;
    private final int characterNumber;
    private final String description;

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
    public PositionedException(
        String className,
        String methodName,
        int lineNumber,
        int characterNumber,
        String description
    ) {
        super(String.format(
            "[%s:%s %d:%d] %s",
            className,
            methodName,
            lineNumber,
            characterNumber,
            description
        ));
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.characterNumber = characterNumber;
        this.description = description;
    }

    /**
     * @return the name of the class that threw this exception
     */
    public String getClassName() {
        return className;
    }

    /**
     * @return the name of the method that threw this exception
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return the input line number when this exception was thrown
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @return the input character number when this exception was thrown
     */
    public int getCharacterNumber() {
        return characterNumber;
    }

    /**
     * @return this exception's description
     */
    public String getDescription() {
        return description;
    }
}
