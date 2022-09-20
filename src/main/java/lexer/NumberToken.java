package lexer;

/**
 * A class for a token that has a string tag and a double value attribute
 */
public class NumberToken implements Token {

    private final double value;
    // the relative (to the expected value) tolerance for value differences in fuzzy equality
    private static final double TOLERANCE = 1e6;

    /**
     * A constructor that initialises this token's value.
     * @param value this token's value
     */
    public NumberToken(double value) {
        this.value = value;
    }

    /**
     * @return this token's tag
     */
    @Override
    public String getTag() {
        return "NUMBER";
    }

    /**
     * @return this token's value
     */
    public double getValue() {
        return value;
    }

    /**
     * Determines whether this token fuzzy equals another: delta is this token's absolute value, scaled by `TOLERANCE`.
     * @param other an object
     * @return whether this token is fuzzily equal to the object
     */
    @Override
    public boolean fuzzyEquals(Object other) {
        // return `true` iff `other` is a `NumberToken` object that has the same tag and the tokens' values absolutely
        // differ by less than this token's absolute value times `TOLERANCE`
        if (!(other instanceof NumberToken otherTagToken)) { return false; }
        return fuzzyEquals(otherTagToken, Math.abs(getValue() * TOLERANCE));
    }

    /**
     * Determines whether this token is fuzzy equals another: their values absolutely differ at most a delta.
     * @param other an object
     * @param delta the positive absolute delta
     * @return whether this token is fuzzily equal to the object
     */
    public boolean fuzzyEquals(Object other, double delta) {
        // return `true` iff `other` is a `NumberToken` object that has the same tag and the tokens' values absolutely
        // differ by less than a delta
        if (!(other instanceof NumberToken otherNumberToken)) { return false; }
        return (
                otherNumberToken.getTag() == getTag() &&
                Math.abs(otherNumberToken.getValue() - getValue()) <= delta
        );
    }

    /**
     * Determines whether this token is structurally equal (has the same value and tag as) to another object.
     * @param other an object
     * @return whether this token is equal to the object
     */
    @Override
    public boolean equals(Object other) {
        // return `true` iff `other` is a `NumberToken` object that has the same tag and value as this token
        if (!(other instanceof NumberToken otherNumberToken)) { return false; }
        return getTag().equals(otherNumberToken.getTag()) && getValue() == otherNumberToken.getValue();
    }

    /**
     * Retrieves the string representation of this token.
     * The string is "[`tag`] value=`value`".
     * @return this token's string representation
     */
    @Override
    public String toString() {
        return "[" + getTag() + "] value=" + this.value;
    }

}
