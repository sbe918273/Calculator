package parser.symbol;

import java.util.List;

import lexer.token.ExpressionTokenTag;
import lexer.token.MinusToken;
import lexer.token.PlusToken;
import lexer.token.PowerToken;

/**
 * A class for the nonterminal representing an exponentiation.
 */
public class PowerNonterminal extends ExpressionNonterminal {

    /**
     * A constructor to initialise this nonterminal's children to be the first operand nonterminal, a new power terminal
     * and the second operand nonterminal. This nonterminal's value is the first operand's to the power of the second's.
     * @param firstOperand the first operand nonterminal
     * @param secondOperand the second operand nonterminal
     */
    public PowerNonterminal(
        ExpressionNonterminal firstOperand,
        ExpressionNonterminal secondOperand
    ) {
        this(
            firstOperand,
            secondOperand,
            new Terminal<>(new PowerToken())
        );
    }

    /**
     * A constructor to initialise this nonterminal's children to be the first operand nonterminal, an operator terminal
     * and the second operand nonterminal. This nonterminal's value is the first operand's to the power of the second's.
     * @param firstOperand the first operand nonterminal
     * @param secondOperand the second operand nonterminal
     * @param operatorTerminal an operator terminal
     */
    public PowerNonterminal(
        ExpressionNonterminal firstOperand,
        ExpressionNonterminal secondOperand,
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorTerminal
    ) {
        super(
            List.of(firstOperand, operatorTerminal, secondOperand),
            Math.pow(firstOperand.getValue(), secondOperand.getValue())
        );
    }
}
