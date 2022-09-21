package parser;

import lexer.ExpressionTokenTag;

import java.util.List;

/**
 * A class for a nonterminal appearing in an expression.
 */
public class ExpressionNonterminal extends Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> {

    // the double value of this expression
    private final double value;

    /**
     * A constructor to initialise this nonterminal's tag and children.
     * @param tag this nonterminal's tag
     * @param children this nonterminal's children
     */
    public ExpressionNonterminal(
        ExpressionNonterminalTag tag,
        List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children,
        double value
    ) {
        super(tag, children);
        this.value = value;
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
}
