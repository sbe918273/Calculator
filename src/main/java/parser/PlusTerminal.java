package parser;

import lexer.ExpressionTokenTag;
import lexer.PlusToken;

/**
 * A class for a plus terminal.
 */
public class PlusTerminal extends Terminal<ExpressionTokenTag, ExpressionNonterminalTag> {
    /**
     * A constructor to initialise this terminal's token to a plus token.
     */
    public PlusTerminal() {
        super(new PlusToken());
    }
}
