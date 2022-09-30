package parser.production;

import lexer.token.CosineToken;
import lexer.token.ExpressionTokenTag;
import parser.symbol.*;


import java.util.List;

/**
 * A class for the expression production of "E -> cos E".
 */
public class CosineProduction extends Production<ExpressionTokenTag, ExpressionNonterminalTag> {

    /**
     * A constructor to initialise this production's name, tag and length.
     */
    public CosineProduction() {
        super("E -> cos E", ExpressionNonterminalTag.EXPRESSION, 2);
    }

    /**
     * Creates the nonterminal head of this production from its symbol sequence.
     * The sequence is a cosine terminal preceding an expression nonterminal.
     * The resulting nonterminal's value is the cosine of the expression nonterminal's value.
     * @param children the nonterminal's children
     * @return the resulting nonterminal
     */
    @Override
    public CosineNonterminal createNonterminal(
            List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
    ) {
        // assert that `children` is not null
        if (children == null) {
            throw new IllegalArgumentException("[CosineProduction:createNonterminal] Child list cannot be null.");
        }
        // assert that `children` has exactly two symbols
        if (children.size() != 2) {
            throw new IllegalArgumentException("[CosineProduction:createNonterminal] Exactly two children required.");
        }

        // assert that the operator symbol is a cosine terminal
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorSymbol = children.get(0).getTerminal();
        if (operatorSymbol == null || !(operatorSymbol.getToken() instanceof CosineToken)) {
            throw new IllegalArgumentException(
                    "[CosineProduction:createNonterminal] Operator symbol is not a cosine terminal."
            );
        }

        // assert that the operand symbol is an expression nonterminal
        Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> operandSymbol = children.get(1).getNonterminal();
        if (!(operandSymbol instanceof ExpressionNonterminal nonterminal)) {
            throw new IllegalArgumentException(
                    "[CosineProduction:createNonterminal] Operand symbol is not an expression nonterminal."
            );
        }

        // The parent expression nonterminal's value is the cosine of the operand symbol's value.
        return new CosineNonterminal(nonterminal);
    }
}
