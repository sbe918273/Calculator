package parser;

import lexer.Token;

/**
 * A class for a terminal that has a token.
 */
public class Terminal implements Symbol {

    // this terminal's token
    private final Token token;

    /**
     * A constructor to initialise this terminal's token
     * @param token a token
     */
    public Terminal(Token token) {
        this.token = token;
    }

    /**
     * @return this terminal's tag
     */
    @Override
    public String getTag() {
        return token.getTag();
    }
}
