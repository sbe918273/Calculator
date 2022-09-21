package parser;

import lexer.ExpressionTokenTag;
import lexer.PlusToken;


import java.util.List;

/**
 * A class for the expression production of "E -> E + E".
 */
public class PlusProduction extends Production<ExpressionTokenTag, ExpressionNonterminalTag> {

    /**
     * A constructor to initialise this production's tag and length. The production has no name.
     * @param tag the production's tag
     * @param length the production's length
     */
    public PlusProduction(ExpressionNonterminalTag tag, int length) {
        super(null, tag, length);
    }

    /**
     * A constructor to initialise this production's name, tag and length. The production has no name.
     * @param name the production's name
     * @param tag the production's tag
     * @param length the production's length
     */
    public PlusProduction(String name, ExpressionNonterminalTag tag, int length) {
        super(name, tag, length);
    }

    @Override
    public Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> createNonterminal(
            List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
    ) {

        // ensure children length is 3
        // ensure first and last are expression
        // ensure middle is +

        if (children == null) {
            throw new IllegalArgumentException("[PlusProduction:createNonterminal] Child list cannot be null.");
        }
        if (children.size() != 3) {
            throw new IllegalArgumentException("[PlusProduction:createNonterminal] Exactly three children required.");
        }

        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> plusTerminal = children.get(1).getTerminal();
        Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag>
            firstNumberToken = children.get(0).getNonterminal(),
            secondNumberToken = children.get(2).getNonterminal();

        if (plusTerminal == null || !plusTerminal.equals(new Terminal<>(new PlusToken()))) {
            throw new IllegalArgumentException("[PlusProduction:createNonterminal] Illegal plus terminal.");
        }
    }
}
