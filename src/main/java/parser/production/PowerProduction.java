package parser.production;

import lexer.token.ExpressionTokenTag;
import lexer.token.PowerToken;
import main.UnpositionedException;
import parser.symbol.*;


import java.util.List;

/**
 * A class for the expression production of "E -> E ^ E".
 */
public class PowerProduction extends Production<ExpressionTokenTag, ExpressionNonterminalTag> {

    /**
     * A constructor to initialise this production's name, tag and length.
     */
    public PowerProduction() {
        super("E -> E ^ E", ExpressionNonterminalTag.EXPRESSION, 3);
    }

    /**
     * Creates the nonterminal head of this production from its symbol sequence.
     * The sequence is a power terminal separating two expression nonterminals.
     * The resulting nonterminal's value is the first operand symbol's value to the power of that of the second.
     * @param children the nonterminal's children
     * @return the resulting nonterminal
     */
    @Override
    public PowerNonterminal createNonterminal(
            List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
    ) throws UnpositionedException {
        // assert that `children` is not null
        if (children == null) {
            throw new UnpositionedException(
                "PowerProduction",
                "createNonterminal",
                "Child list cannot be null."
            );
        }
        // assert that `children` has exactly three symbols
        if (children.size() != 3) {
            throw new UnpositionedException(
                "PowerProduction",
                "createNonterminal",
                "Exactly three children required."
            );
        }

        // assert that the operator symbol is a power terminal
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorSymbol = children.get(1).getTerminal();
        if (operatorSymbol == null || !(operatorSymbol.getToken() instanceof PowerToken)) {
            throw new UnpositionedException(
                "PowerProduction",
                "createNonterminal",
                "Operator symbol is not a power terminal."
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
                "PowerProduction",
                "createNonterminal",
                "Operand symbol is not an expression nonterminal."
            );
        }

        // The parent expression nonterminal's value is the first operand symbol's value to the power of the second's.
        return new PowerNonterminal(firstNonterminal, secondNonterminal, operatorSymbol);
    }
}
