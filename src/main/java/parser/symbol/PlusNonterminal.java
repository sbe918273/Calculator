package parser.symbol;

import java.util.List;

import lexer.token.ExpressionTokenTag;
import lexer.token.PlusToken;

/**
 * A class for the nonterminal representing a sum.
 */
public class PlusNonterminal extends ExpressionNonterminal {

    /**
     * A constructor to initialise this nonterminal's children to be the first operand nonterminal, a new plus terminal
     * and the second operand nonterminal. This nonterminal's value is the sum of those nonterminals' values.
     * @param firstOperand the first operand nonterminal
     * @param secondOperand the second operand nonterminal
     */
    public PlusNonterminal(
            ExpressionNonterminal firstOperand,
            ExpressionNonterminal secondOperand
    ) {
        this(
            firstOperand,
            secondOperand,
            new Terminal<>(new PlusToken())
        );
    }

    /**
     * A constructor to initialise this nonterminal's children to be the first operand nonterminal, an operator terminal
     * and the second operand nonterminal. This nonterminal's value is the sum of those nonterminals' values.
     * @param firstOperand the first operand nonterminal
     * @param secondOperand the second operand nonterminal
     * @param operatorTerminal an operator terminal
     */
    public PlusNonterminal(
        ExpressionNonterminal firstOperand,
        ExpressionNonterminal secondOperand,
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorTerminal
    ) {
        super(
            List.of(firstOperand, operatorTerminal, secondOperand),
            firstOperand.getValue() + secondOperand.getValue()
        );
    }
}
