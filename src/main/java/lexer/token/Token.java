package lexer.token;

import java.util.Iterator;
import java.util.List;

/**
 * An interface for a token that has a tag.
 * @param <TokenTag> a type of tag
 */
public interface Token<TokenTag> {

    /**
     * @return this token's tag
     */
    TokenTag getTag();

    /**
     * Determines whether this token is fuzzily equal to another. Defaults to the existing `equals` method.
     * @param object an object
     * @return whether this token is fuzzily equal to the token
     */
    default boolean fuzzyEquals(Object object) {
        return equals(object);
    }

    /**
     * Determines whether the contents of two token lists are element-wise fuzzily equal.
     * @param observedTokens an observed token list
     * @param expectedTokens an expected token list
     * @param <T> a type of tag
     * @return whether the lists are fuzzily equal
     */
    static <T>boolean fuzzyListEquals(List<Token<T>> observedTokens, List<Token<T>> expectedTokens) {
        // return `true` iff both arrays are `null`
        if (observedTokens == null) {
            return expectedTokens == null;
        } else {
            // return `false` if the lists' length are not equal
            if (observedTokens.size() != expectedTokens.size()) { return false; }
            // return `false` if a token in `expectedTokens` does not fuzzy equal its matching token in `observedTokens`
            // We iterate simultaneously over the token lists using their respective iterators.
            Iterator<Token<T>> observedIterator = observedTokens.iterator();
            Iterator<Token<T>> expectedIterator = expectedTokens.iterator();
            while (observedIterator.hasNext()) {
                Token<T> observedToken = observedIterator.next();
                Token<T> expectedToken = expectedIterator.next();
                // `null` tokens are fuzzily equal
                if (observedToken == null) {
                    if (expectedToken != null) { return false; }
                } else {
                    if (!observedToken.fuzzyEquals(expectedToken)) { return false; }
                }
            }
            // return `true` iff none of the above checks failed
            return true;
        }
    }
}
