package lexer;

import lexer.token.Token;

import java.io.IOException;

/**
 * A class to represent a lexer that generates tokens with attributes.
 * @param <TokenTag> the type of tag for a token that this lexer generates
 */
public interface Lexer<TokenTag> {
    /**
     * Generates a token from the current input.
     * Returns `null` if the lexer reaches its input's end.
     * @return a found token
     * @throws IOException the reader throws an IO exception
     * @throws IllegalLexemeException the current input produces an invalid token
     */
    Token<TokenTag> scan() throws IOException, IllegalLexemeException;

    /**
     * @return the number of the current line
     */
    int getLineNumber();

    /**
     * @return the number of the current character on its line
     */
    int getCharacterNumber();
}
