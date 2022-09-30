package parser.symbol;

import lexer.token.ExpressionTokenTag;

import java.util.List;

/**
 * A class for an expression nonterminal.
 */
public class ExpressionNonterminal extends Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> {

    // the relative (to the expected value) tolerance for value differences in fuzzy equality
    private static final double TOLERANCE = 1e6;

    // the double value of this expression
    private final double value;

    /**
     * A constructor to initialise this nonterminal's tag (`EXPRESSION`) and children.
     * @param children this nonterminal's children
     */
    public ExpressionNonterminal(
        List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children,
        double value
    ) {
        super(ExpressionNonterminalTag.EXPRESSION, children);
        this.value = value;
    }

    /**
     * @return this nonterminal's value
     */
    public double getValue() {
        return value;
    }

    /**
     * Retrieves the string representation of this nonterminal.
     * The string is "[`tag`] value=`value`".
     * @return this nonterminal's string representation
     */
    @Override
    public String toString() {
        return String.format("[%s] value=%f", getTag().toString(), value);
    }

    /**
     * Determines whether this nonterminal is structurally but fuzzily equal to another.
     * Two expression nonterminals are equal iff their tags are equal and their values and children are fuzzily equal.
     * @param other an object
     * @return whether this nonterminal is equal to the object
     */
    @Override
    public boolean fuzzyEquals(Object other) {
        // return false if the object is not a nonterminal
        if (!(other instanceof ExpressionNonterminal nonterminal)) {
            return false;
        }
        // return false if the values differ more than the other token's value, scaled by `TOLERANCE`
        double expectedValue = nonterminal.getValue();
        if (getValue() - expectedValue > Math.abs(expectedValue * TOLERANCE)) {
            return false;
        }
        // return true iff the tags are equal and the children are element-wise equal
        // We suppress warnings for the unchecked cast from `Nonterminal` to `Nonterminal<TerminalTag, NonterminalTag>`
        // The cast is necessary due to type erasure but presents the problem that nonterminals with different type
        // parameters can be equal if those type parameters allow their tags (and those of their children) to be equal.
        return getTag() == nonterminal.getTag() && Symbol.fuzzyListEquals(
                getChildren(),
                nonterminal.getChildren()
        );
    }
}
