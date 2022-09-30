package parser.symbol;

import java.util.List;

import lexer.token.*;
import main.UnpositionedException;

/**
 * A class for the nonterminal representing a factorial expression.
 */
public class FactorialNonterminal extends ExpressionNonterminal {

    // the maximum absolute difference of a number from an integer for a factorial operation on it to be valid
    private static final double DELTA = 1e-6;

    /**
     * Calculates an integer's factorial.
     * @param number an integer
     * @return the integer's factorial
     */
    public static long factorial(double number) {
        // throw an `IllegalFactorialException` iff `number` differs from an integer by more than a delta.
        if (number > -DELTA && Math.abs(number % 1) > DELTA) {
            throw new UnpositionedException(
                    "FactorialProduction",
                    "createNonterminal",
                    "Value of the operand symbol is not a positive integer."
            );
        }
        // initialise the result to `1`
        long result = 1;
        // multiply `result` by every number from `2` to `number` inclusive
        for (int count = 2; count <= number; count++) {
            result *= count;
        }
        return result;
    }


    /**
     * A constructor to initialise this nonterminal's children to an operand nonterminal and a new factorial terminal.
     * This nonterminal's value is the cosine of the operand's value.
     * @param operandTerminal an operand nonterminal
     */
    public FactorialNonterminal(ExpressionNonterminal operandTerminal) {
        this(
            operandTerminal,
            new Terminal<>(new FactorialToken())
        );
    }

    /**
     * A constructor to initialise this nonterminal's children to an operand nonterminal and an operator terminal.
     * This nonterminal's value is the cosine of the operand's value.
     * @param operandNonterminal an operand nonterminal
     * @param operatorTerminal an operator terminal
     */
    public FactorialNonterminal(
        ExpressionNonterminal operandNonterminal,
        Terminal<ExpressionTokenTag, ExpressionNonterminalTag> operatorTerminal
    ) {
        super(
            List.of(operandNonterminal, operatorTerminal),
            factorial(operandNonterminal.getValue())
        );
    }
}
