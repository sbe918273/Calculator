package parser.production;

import lexer.token.ExpressionTokenTag;
import lexer.token.PlusToken;
import main.UnpositionedException;
import parser.symbol.*;


import java.util.List;

/**
 * A class for the expression production of "E -> E + E".
 */
public class PlusProduction extends Production<ExpressionTokenTag, ExpressionNonterminalTag> {

    /**
     * A constructor to initialise this production's name, tag and length.
     */
    public PlusProduction() {
        super("E -> E + E", ExpressionNonterminalTag.EXPRESSION, 3);
    }

    /**
     * Creates the nonterminal head of this production from its symbol sequence.
     * The sequence is a plus terminal separating two expression nonterminals.
     * The resulting nonterminal's value is the sum of the two operand nonterminals' values.
     * @param children the nonterminal's children
     * @return the resulting nonterminal
     */
    @Override
    public PlusNonterminal createNonterminal(
        List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
    ) throws UnpositionedException {
        // assert that `children` is not null
        if (children == null) {
            throw new UnpositionedException(
                "PlusProduction",
                "createNonterminal",
                "Child list cannot be null."
            );
        }
        // assert that `children` has exactly three symbols
        if (children.size() != 3) {
            throw new UnpositionedException(
                "PlusProduction",
                "createNonterminal",
                "Exactly three children required."
            );
        }

        // assert that the operator symbol is a plus terminal
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorSymbol = children.get(1).getTerminal();
        if (operatorSymbol == null || !(operatorSymbol.getToken() instanceof PlusToken)) {
            throw new UnpositionedException(
                "PlusProduction",
                "createNonterminal",
                "Operator symbol is not a plus terminal."
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
                "PlusProduction",
                "createNonterminal",
                "Operand symbol is not an expression nonterminal."
            );
        }

        // The parent expression nonterminal's value is the sum of the two operand symbols' values.
        return new PlusNonterminal(firstNonterminal, secondNonterminal, operatorSymbol);
    }
}
