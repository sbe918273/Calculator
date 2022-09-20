package lexer;

/**
 * A class for a token that only has a string tag.
 */
public class TagToken implements Token {

    private final String tag;

    /**
     * A constructor that initialises this token's tag.
     * @param tag this token's tag
     */
    public TagToken(String tag) {
        this.tag = tag;
    }

    /**
     * @return this token's tag
     */
    @Override
    public String getTag() {
        return tag;
    }

    /**
     * Retrieves the string representation of this token.
     * The string is "[`tag`]".
     * @return this token's string representation
     */
    @Override
    public String toString() {
        return "[" + getTag() + "]";
    }

    /**
     * Determines whether this token is structurally equal (has the same tag as) to another object
     * @param other an object
     * @return whether this token is equal to the object
     */
    @Override
    public boolean equals(Object other) {
        // return `true` iff `other` is a `TagToken` object that has the same tag as this token
        if (!(other instanceof TagToken otherTagToken)) { return false; }
        return getTag().equals(otherTagToken.getTag());
    }
}
