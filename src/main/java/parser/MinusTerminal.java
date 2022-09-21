package parser;

import lexer.ExpressionTokenTag;
import lexer.MinusToken;

/**
 * A class for a minus terminal.
 */
public class MinusTerminal extends Terminal<ExpressionTokenTag, ExpressionNonterminalTag> {
    /**
     * A constructor to initialise this terminal's token to a minus token.
     */
    public MinusTerminal() {
        super(new MinusToken());
    }
}
