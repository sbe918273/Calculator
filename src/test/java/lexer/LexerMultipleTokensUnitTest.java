package lexer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class LexerMultipleTokensUnitTest {

    private final String inputString;
    private final Token[] expectedTokens;

    public LexerMultipleTokensUnitTest(String inputString, Token[] expectedTokens) {
        this.inputString = inputString;
        this.expectedTokens = expectedTokens;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                // `Lexer` skips whitespace.
                {
                    " cos\r\n4\t^",
                    new Token[] {
                        new TagToken("COSINE"),
                        new NumberToken(4),
                        new TagToken("POWER"),
                    }
                },
                // `Lexer` attempts to interpret the first sign after a number as an operand.
                {
                    "30e-1+6",
                    new Token[] {
                        new NumberToken(30e-1),
                        new TagToken("PLUS"),
                        new NumberToken(6),
                    }
                },
                // `Lexer` can interpret the first sign after an operand as being an operand.
                {
                    "3+^4",
                    new Token[] {
                        new NumberToken(3),
                        new TagToken("PLUS"),
                        new TagToken("POWER"),
                        new NumberToken(4),
                    },
                },
                // `Lexer` attempts to interpret the last sign after a number to be that of a number.
                {
                    "10e-1+-2",
                    new Token[] {
                        new NumberToken(10e-1),
                        new TagToken("PLUS"),
                        new NumberToken(-2),
                    },
                },
        });
    }

    @Test
    public void testLexerMultipleTokens() throws IOException, InvalidTokenException {
        // ARRANGE
        Lexer lexer = new Lexer(inputString);
        // ACTION
        Token[] observedTokens = lexer.completeScan();
        // ASSERT
        Assert.assertTrue(Token.fuzzyArrayEquals(observedTokens, expectedTokens));
    }
}
