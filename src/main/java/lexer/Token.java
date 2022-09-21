package lexer;

/**
 * An interface for a token that has a tag.
 * @param <T> a type of tag
 */
public interface Token<T> {

    /**
     * @return this token's tag
     */
    T getTag();

    /**
     * Determines whether this token is fuzzily equal to another. Defaults to the existing `equals` method.
     * @param other an object
     * @return whether this token is fuzzily equal to the object
     */
    default boolean fuzzyEquals(Object other) {
        return equals(other);
    }

    /**
     * Determines whether two token arrays are element-wise fuzzily equal.
     * @param observedTokens an observed token array
     * @param expectedTokens an expected token array
     * @param <T> a type of tag
     * @return whether the arrays are fuzzily equal
     */
    static <T>boolean fuzzyArrayEquals(Token<T>[] observedTokens, Token<T>[] expectedTokens) {
        // return `true` iff both arrays are `null`
        if (expectedTokens == null) {
            return observedTokens == null;
        } else {
            // return `false` if the arrays' length are not equal
            if (expectedTokens.length != observedTokens.length) { return false; }
            // return `false` if a token in `expectedTokens` does not fuzzy equal its matching token in `observedTokens`
            for (int index = 0; index < expectedTokens.length; index++) {
                Token<T> expectedToken = expectedTokens[index];
                Token<T> observedToken = observedTokens[index];
                // `null` tokens are fuzzily equal
                if (expectedToken == null) {
                    if (observedToken != null) { return false; }
                } else {
                    if (!expectedToken.fuzzyEquals(observedToken)) { return false; }
                }
            }
            // return `true` iff none of the above checks failed
            return true;
        }
    }
}
