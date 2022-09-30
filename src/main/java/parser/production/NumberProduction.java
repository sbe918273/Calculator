package parser.production;

import java.util.List;

import lexer.token.ExpressionTokenTag;
import lexer.token.NumberToken;
import main.UnpositionedException;
import parser.symbol.*;

/**
 * A class for the expression production of "E -> number".
 */
public class NumberProduction extends Production<ExpressionTokenTag, ExpressionNonterminalTag> {

    /**
     * A constructor to initialise this production's name, tag and length.
     */
    public NumberProduction() {
        super("E -> number", ExpressionNonterminalTag.EXPRESSION, 1);
    }

    /**
     * Creates the nonterminal head of this production from its symbol sequence: a number terminal.
     * The resulting nonterminal's value is the number terminal's value.
     * @param children the nonterminal's children
     * @return the resulting nonterminal
     */
    @Override
    public Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> createNonterminal(
        List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
    ) {
        // assert that `children` is not null
        if (children == null) {
            throw new UnpositionedException(
                "NumberProduction",
                "createNonterminal",
                "Child list cannot be null."
            );
        }
        // assert that `children` has exactly one symbol
        if (children.size() != 1) {
            throw new UnpositionedException(
                "NumberProduction",
                "createNonterminal",
                "Exactly two children required."
            );
        }

        // assert that the symbol is a number terminal
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operandTerminal = children.get(0).getTerminal();
        if (operandTerminal == null || !(operandTerminal.getToken() instanceof NumberToken)) {
            throw new UnpositionedException(
                "NumberProduction",
                "createNonterminal",
                "Symbol is not a number terminal."
            );
        }

        // The parent expression nonterminal's value is the number terminal's value.
        return new NumberNonterminal(operandTerminal);
    }
}
