package parser.production;

import lexer.token.ExpressionTokenTag;
import lexer.token.MinusToken;
import main.UnpositionedException;
import parser.symbol.*;


import java.util.List;

/**
 * A class for the expression production of "E -> E - E".
 */
public class MinusProduction extends Production<ExpressionTokenTag, ExpressionNonterminalTag> {

    /**
     * A constructor to initialise this production's name, tag and length.
     */
    public MinusProduction() {
        super("E -> E - E", ExpressionNonterminalTag.EXPRESSION, 3);
    }

    /**
     * Creates the nonterminal head of this production from its symbol sequence.
     * The sequence is a minus terminal separating two expression nonterminals.
     * The resulting nonterminal's value is first operand symbol's value minus that of the second.
     * @param children the nonterminal's children
     * @return the resulting nonterminal
     */
    @Override
    public MinusNonterminal createNonterminal(
            List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
    ) {
        // assert that `children` is not null
        if (children == null) {
            throw new UnpositionedException(
                "MinusProduction",
                "createNonterminal",
                "Child list cannot be null."
            );
        }
        // assert that `children` has exactly three symbols
        if (children.size() != 3) {
            throw new UnpositionedException(
                "MinusProduction",
                "createNonterminal",
                "Exactly three children required."
            );
        }

        // assert that the operator symbol is a minus terminal
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorSymbol = children.get(1).getTerminal();
        if (operatorSymbol == null || !(operatorSymbol.getToken() instanceof MinusToken)) {
            throw new UnpositionedException(
                "MinusProduction",
                "createNonterminal",
                "Operator symbol is not a minus terminal."
            );
        }

        // assert that the operand symbols are expression nonterminals
        Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag>
                firstOperandSymbol = children.get(0).getNonterminal(),
                secondOperandSymbol = children.get(2).getNonterminal();
        if (
                !(firstOperandSymbol instanceof ExpressionNonterminal firstNonterminal) ||
                        !(secondOperandSymbol instanceof ExpressionNonterminal secondNonterminal)
        ) {
            throw new UnpositionedException(
                "MinusProduction",
                "createNonterminal",
                "Operand symbol is not an expression nonterminal."
            );
        }

        // The parent expression nonterminal's value is first operand symbol's value minus that of the second.
        return new MinusNonterminal(firstNonterminal, secondNonterminal, operatorSymbol);
    }
}
