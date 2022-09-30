package lexer.token;

/**
 * A class for a minus token.
 */
public class MinusToken implements Token<ExpressionTokenTag> {
    /**
     * @return this token's fixed tag: `MINUS`
     */
    @Override
    public final ExpressionTokenTag getTag() {
        return ExpressionTokenTag.MINUS;
    }

    /**
     * @return whether this token is equal to another object (whether that object is a minus token)
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof MinusToken;
    }

    /**
     * @return this token's string representation
     */
    @Override
    public String toString() {
        return "[MINUS]";
    }
}
