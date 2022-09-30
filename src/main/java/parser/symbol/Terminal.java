package parser.symbol;

import lexer.token.Token;

/**
 * A class for a terminal that has a token.
 * We require `NonterminalTag` so that we can store this terminal with nonterminals in an appropriate symbol array.
 * @param <TerminalTag> the type of tag for (the token of) this terminal
 * @param <NonterminalTag> the type of for this terminal's encompassing nonterminal
 */
public class Terminal<TerminalTag, NonterminalTag> implements Symbol<TerminalTag, NonterminalTag> {

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
     * Determines whether this terminal is structurally equal to another (their tokens are equal).
     * @param other an object
     * @return whether this terminal is equal to the object
     */
    @Override
    public boolean equals(Object other) {
        // return `false` if the object is not a terminal
        if (!(other instanceof Terminal terminal)) {
            return false;
        }
        // return `true` iff the terminals' tokens are equal
        return getToken().equals(terminal.getToken());
    }

    /**
     * Determines whether this terminal is structurally but fuzzily equal to another (their tokens are fuzzily equal).
     * @param other an object
     * @return whether this terminal is equal to the object
     */
    @Override
    public boolean fuzzyEquals(Object other) {
        // return `false` if the object is not a terminal
        if (!(other instanceof Terminal terminal)) {
            return false;
        }
        // return `true` iff the terminals' tokens are fuzzily equal
        return getToken().fuzzyEquals(terminal.getToken());
    }
}
