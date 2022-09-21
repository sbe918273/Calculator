package parser;

import lexer.Token;

/**
 * A class for a terminal that has a token.
 * We require `NonterminalTag` so that we can store this terminal with nonterminals in an appropriate symbol array.
 * @param <TerminalTag> the type of tag for (the token of) this terminal
 * @param <NonterminalTag> the type of for this terminal's encompassing nonterminal
 */
class Terminal<TerminalTag, NonterminalTag> implements Symbol<TerminalTag, NonterminalTag> {

    final private Token<TerminalTag> token;

    /**
     * A constructor to initialise this terminal's token
     * @param token a terminal of appropriate tag types
     */
    public Terminal(Token<TerminalTag> token) {
        this.token = token;
    }

    /**
     * @return this terminal's tag
     */
    public TerminalTag getTag() {
        return this.token.getTag();
    }

    /**
     * @return this terminal's token
     */
    public Token<TerminalTag> getToken() {
        return this.token;
    }

    /**
     * @return this terminal
     */
    @Override
    public Terminal<TerminalTag, NonterminalTag> getTerminal() {
        return this;
    }

    /**
     * Determines whether this terminal is structurally equal (has an equal token to) another terminal.
     * @param other a terminal
     * @return whether this terminal is equal to the other terminal
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Terminal terminal)) { return false; }
        return getToken().equals(terminal.getToken());
    }
}
