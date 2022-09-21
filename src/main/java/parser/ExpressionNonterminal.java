package parser;

import lexer.ExpressionTokenTag;

import java.util.List;

/**
 * A class for a nonterminal appearing in an expression.
 */
public class ExpressionNonterminal implements Nonterminal<ExpressionTokenTag> {

    // this nonterminal's tag
    private final ExpressionTokenTag tag;
    // this nonterminal's children (the symbols appearing in the same order in this nonterminal's production)
    private final List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children;

    /**
     * A constructor to initialise this nonterminal's tag and children
     * @param tag this nonterminal's tag
     * @param children this nonterminal's children
     */
    public ExpressionNonterminal(
        ExpressionTokenTag tag,
        List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
    ) {
        this.tag = tag;
        this.children = children;
    }

    /**
     * @return this nonterminal's tag
     */
    @Override
    public ExpressionTokenTag getTag() {
        return tag;
    }

    /**
     * @return this nonterminal's children
     */
    public List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> getChildren() {
        return children;
    }

    /**
     * Retrieves the string representation of this nonterminal.
     * The string is "[`tag`]".
     * @return this nonterminal's string representation
     */
    @Override
    public String toString() {
        return "[" + getTag() + "]";
    }
}
