package lexer;

/**
 * A class for a plus token.
 */
public class PlusToken implements Token<ExpressionTokenTag> {
    /**
     * @return this token's fixed tag: `PLUS`
     */
    @Override
    public final ExpressionTokenTag getTag() {
        return ExpressionTokenTag.PLUS;
    }

    /**
     * @return whether this token is equal to another object (whether that object is a plus token)
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof PlusToken;
    }

    /**
     * @return this token's string representation
     */
    @Override
    public String toString() {
        return "[PLUS]";
    }
}
