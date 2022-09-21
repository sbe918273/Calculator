package parser;

import lexer.ExpressionTokenTag;
import lexer.PowerToken;

/**
 * A class for a power terminal.
 */
public class PowerTerminal extends Terminal<ExpressionTokenTag, ExpressionNonterminalTag> {
    /**
     * A constructor to initialise this terminal's token to a power token.
     */
    public PowerTerminal() {
        super(new PowerToken());
    }
}
