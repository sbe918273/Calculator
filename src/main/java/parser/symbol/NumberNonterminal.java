package parser.symbol;

import java.util.List;

import lexer.token.*;

/**
 * A class for the nonterminal representing a number.
 */
public class NumberNonterminal extends ExpressionNonterminal {

    /**
     * A constructor to initialise this nonterminal's children to a terminal containing a number token of a value.
     * @param value this number's value
     */
    public NumberNonterminal(double value) {
        this(new NumberToken(value));
    }

    /**
     * A constructor to initialise this nonterminal's children to a terminal containing a number token.
     * @param numberToken a number token
     */
    public NumberNonterminal(NumberToken numberToken) {
        this(new Terminal<>(numberToken));
    }

    /**
     * A constructor to initialise this nonterminal's children to a number terminal.
     * @param numberTerminal a number terminal: its token must be a number token.
     */
    public NumberNonterminal(Terminal<ExpressionTokenTag, ExpressionNonterminalTag> numberTerminal) {
        super(
            List.of(numberTerminal),
            ((NumberToken)numberTerminal.getToken()).getValue()
        );
    }
}
