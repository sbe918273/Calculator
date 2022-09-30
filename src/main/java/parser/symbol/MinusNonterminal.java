package parser.symbol;

import java.util.List;

import lexer.token.ExpressionTokenTag;
import lexer.token.MinusToken;
import lexer.token.PlusToken;

/**
 * A class for the nonterminal representing a subtraction.
 */
public class MinusNonterminal extends ExpressionNonterminal {

    /**
     * A constructor to initialise this nonterminal's children to be the first operand nonterminal, a new minus terminal
     * and the second operand nonterminal. This nonterminal's value is the first nonterminal's value minus the second's.
     * @param firstOperand the first operand nonterminal
     * @param secondOperand the second operand nonterminal
     */
    public MinusNonterminal(
        ExpressionNonterminal firstOperand,
        ExpressionNonterminal secondOperand
    ) {
        this(
            firstOperand,
            secondOperand,
            new Terminal<>(new MinusToken())
        );
    }

    /**
     * A constructor to initialise this nonterminal's children to be the first operand nonterminal, an operator terminal
     * and the second operand nonterminal. This nonterminal's value is the first nonterminal's value minus the second's.
     * @param firstOperand the first operand nonterminal
     * @param secondOperand the second operand nonterminal
     * @param operatorTerminal an operator terminal
     */
    public MinusNonterminal(
        ExpressionNonterminal firstOperand,
        ExpressionNonterminal secondOperand,
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorTerminal
    ) {
        super(
            List.of(firstOperand, operatorTerminal, secondOperand),
            firstOperand.getValue() - secondOperand.getValue()
        );
    }
}
