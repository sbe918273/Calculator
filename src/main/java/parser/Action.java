package parser;

import lexer.InvalidTokenException;

import java.io.IOException;

/**
 * A class for an action of an SLR parsing table. For example, shift, reduce, accept or error.
 */
public interface Action {

    /**
     * Executes this action. For example, by updating the state of an outer `Parser` object and/or reading a token.
     * @throws IOException the lexer's reader throws an IO exception
     * @throws InvalidTokenException the lexer encounters an invalid token
     */
    void execute() throws IOException, InvalidTokenException;
}
