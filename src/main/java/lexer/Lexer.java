package lexer;

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
     * @throws InvalidTokenException the current input produces an invalid token
     */
    Token<TokenTag> scan() throws IOException, InvalidTokenException;
}
