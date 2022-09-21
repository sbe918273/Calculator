package lexer;

/**
 * A class for a factorial token.
 */
public class FactorialToken implements Token<ExpressionTokenTag> {
    /**
     * @return this token's fixed tag: `FACTORIAL`
     */
    @Override
    public final ExpressionTokenTag getTag() {
        return ExpressionTokenTag.FACTORIAL;
    }

    /**
     * @return whether this token is equal to another object (whether that object is a factorial token)
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof FactorialToken;
    }

    /**
     * @return this token's string representation
     */
    @Override
    public String toString() {
        return "[FACTORIAL]";
    }
}
