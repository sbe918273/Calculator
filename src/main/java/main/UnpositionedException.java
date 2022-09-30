package main;

/**
 * An unchecked exception that has attributes of its description the names of the class and method that threw it.
 */
public class UnpositionedException extends RuntimeException {

    private final String className;
    private final String methodName;
    private final String description;

    /**
     * Initialises the names of the class and method that threw this exception, the input line and number when the
     * exception was thrown and the exception's description. We initialise the resulting exception's message to be
     * "[`className`:`methodName` `lineNumber`:`characterNumber`] `description`".
     * @param className the name of the class that threw this exception
     * @param methodName the name of the method that threw this exception
     * @param description this exception's description
     */
    public UnpositionedException(
            String className,
            String methodName,
            String description
    ) {
        super(String.format(
                "[%s:%s] %s",
                className,
                methodName,
                description
        ));
        this.className = className;
        this.methodName = methodName;
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
     * @return this exception's description
     */
    public String getDescription() {
        return description;
    }
}
