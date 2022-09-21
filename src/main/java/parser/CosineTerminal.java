package parser;

import lexer.ExpressionTokenTag;
import lexer.CosineToken;

/**
 * A class for a cosine terminal.
 */
public class CosineTerminal extends Terminal<ExpressionTokenTag, ExpressionNonterminalTag> {
    /**
     * A constructor to initialise this terminal's token to a cosine token.
     */
    public CosineTerminal() {
        super(new CosineToken());
    }
}
