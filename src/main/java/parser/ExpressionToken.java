package parser;

import lexer.ExpressionTokenTag;
import lexer.Token;

/**
 * A class for a terminal appearing in an expression.
 */
public class ExpressionTerminal implements Terminal<ExpressionTokenTag> {

    // this terminal's token
    private final Token<ExpressionTokenTag> token;

    /**
     * A constructor to initialise this terminal's token
     * @param token a token
     */
    public ExpressionTerminal(Token<ExpressionTokenTag> token) {
        this.token = token;
    }

    /**
     * @return this terminal's tag
     */
    @Override
    public ExpressionTokenTag getTag() {
        return token.getTag();
    }

    /**
     * @return this terminal's token
     */
    @Override
    public Token<ExpressionTokenTag> getToken() {
        return token;
    }

    /**
     * @return this terminal's string representation (that of its token)
     */
    @Override
    public String toString() {
        return token.toString();
    }
}
