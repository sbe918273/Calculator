package parser.production;

import java.util.List;

import lexer.token.ExpressionTokenTag;
import lexer.token.FactorialToken;
import main.UnpositionedException;
import parser.symbol.*;

/**
 * A class for the expression production of "E -> E!".
 */
public class FactorialProduction extends Production<ExpressionTokenTag, ExpressionNonterminalTag> {

    /**
     * A constructor to initialise this production's name, tag and length.
     */
    public FactorialProduction() {
        super("E -> E!", ExpressionNonterminalTag.EXPRESSION, 2);
    }

    /**
     * Creates the nonterminal head of this production from its symbol sequence.
     * The sequence is an expression nonterminal preceding a factorial terminal.
     * The resulting nonterminal's value is the factorial of the expression nonterminal's value.
     * @param children the nonterminal's children
     * @return the resulting nonterminal
     */
    @Override
    public Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> createNonterminal(
            List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
    ) throws UnpositionedException {
        // assert that `children` is not null
        if (children == null) {
            throw new UnpositionedException(
                "FactorialProduction",
                "createNonterminal",
                "Child list cannot be null."
            );
        }
        // assert that `children` has exactly two symbols
        if (children.size() != 2) {
            throw new UnpositionedException(
                "FactorialProduction",
                "createNonterminal",
                "Exactly two children required."
            );
        }

        // assert that the operator symbol is a factorial terminal
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorSymbol = children.get(1).getTerminal();
        if (operatorSymbol == null || !(operatorSymbol.getToken() instanceof FactorialToken)) {
            throw new UnpositionedException(
                    "FactorialProduction",
                    "createNonterminal",
                    "Operator symbol is not a factorial terminal."
            );
        }

        // assert that the operand symbol is an expression nonterminal
        Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> operandSymbol = children.get(0).getNonterminal();
        if (!(operandSymbol instanceof ExpressionNonterminal operandNonterminal)) {
            throw new UnpositionedException(
                    "FactorialProduction",
                    "createNonterminal",
                    "Operand symbol is not an expression nonterminal."
            );
        }

        // The parent expression nonterminal's value is the factorial of the operand symbol's value.
        return new FactorialNonterminal(operandNonterminal, operatorSymbol);
    }
}
