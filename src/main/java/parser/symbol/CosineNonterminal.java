package parser.symbol;

import java.util.List;

import lexer.token.CosineToken;
import lexer.token.ExpressionTokenTag;
import lexer.token.MinusToken;
import lexer.token.PlusToken;

/**
 * A class for the nonterminal representing a cosine expression.
 */
public class CosineNonterminal extends ExpressionNonterminal {

    /**
     * A constructor to initialise this nonterminal's children to a new cosine terminal and an operand nonterminal.
     * This nonterminal's value is the cosine of the operand's value.
     * @param operandTerminal an operand nonterminal
     */
    public CosineNonterminal(ExpressionNonterminal operandTerminal) {
        this(
            operandTerminal,
            new Terminal<>(new CosineToken())
        );
    }

    /**
     * A constructor to initialise this nonterminal's children to an operator terminal and an operand nonterminal.
     * This nonterminal's value is the cosine of the operand's value.
     * @param operandNonterminal an operand nonterminal
     * @param operatorTerminal an operator terminal
     */
    public CosineNonterminal(
        ExpressionNonterminal operandNonterminal,
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorTerminal
    ) {
        super(
            List.of(operatorTerminal, operandNonterminal),
            Math.cos(operandNonterminal.getValue())
        );
    }
}
