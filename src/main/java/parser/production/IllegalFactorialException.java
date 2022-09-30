package parser.production;

import main.PositionedException;
import main.UnpositionedException;

/**
 * An exception to throw if we attempt to take the factorial of a nonpositive and/or noninteger number.
 */
public class IllegalFactorialException extends UnpositionedException {
    public IllegalFactorialException(
            String className,
            String methodName,
            String description
    ) {
        super(className, methodName, description);
    }
}
