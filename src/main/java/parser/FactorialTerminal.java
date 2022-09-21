package parser;

import lexer.ExpressionTokenTag;
import lexer.FactorialToken;

/**
 * A class for a factorial terminal.
 */
public class FactorialTerminal extends Terminal<ExpressionTokenTag, ExpressionNonterminalTag> {
    /**
     * A constructor to initialise this terminal's token to a factorial token.
     */
    public FactorialTerminal() {
        super(new FactorialToken());
    }
}
