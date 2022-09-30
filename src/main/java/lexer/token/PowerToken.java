package lexer.token;

/**
 * A class for a power token.
 */
public class PowerToken implements Token<ExpressionTokenTag> {
    /**
     * @return this token's fixed tag: `POWER`
     */
    @Override
    public final ExpressionTokenTag getTag() {
        return ExpressionTokenTag.POWER;
    }

    /**
     * @return whether this token is equal to another object (whether that object is a power token)
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof PowerToken;
    }

    /**
     * @return this token's string representation
     */
    @Override
    public String toString() {
        return "[POWER]";
    }
}
