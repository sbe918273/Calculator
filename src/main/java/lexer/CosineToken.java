package lexer;

/**
 * A class for a cosine token.
 */
public class CosineToken implements Token<ExpressionTokenTag> {
    /**
     * @return this token's fixed tag: `COSINE`
     */
    @Override
    public final ExpressionTokenTag getTag() {
        return ExpressionTokenTag.COSINE;
    }

    /**
     * @return whether this token is equal to another object (whether that object is a cosine token)
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof CosineToken;
    }

    /**
     * @return this token's string representation
     */
    @Override
    public String toString() {
        return "[COSINE]";
    }
}
