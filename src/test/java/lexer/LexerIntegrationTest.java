package lexer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class LexerIntegrationTest {

    private final String inputString;
    private final Token[] expectedTokens;

    public LexerIntegrationTest(String inputString, Token[] expectedTokens) {
        this.inputString = inputString;
        this.expectedTokens = expectedTokens;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            {
                "cos90!+\t3.45e-9\n--87^4 +.0001!4.e+1",
                new Token[] {
                    new TagToken(TokenTag.COSINE),
                    new NumberToken(90),
                    new TagToken(TokenTag.FACTORIAL),
                    new TagToken(TokenTag.PLUS),
                    new NumberToken(3.45e-9),
                    new TagToken(TokenTag.MINUS),
                    new NumberToken(-87),
                    new TagToken(TokenTag.POWER),
                    new NumberToken(4),
                    new TagToken(TokenTag.PLUS),
                    new NumberToken(.0001),
                    new TagToken(TokenTag.FACTORIAL),
                    new NumberToken(4.e+1),
                },
            },
            {
                "!+2.46--45e-4-45!cos",
                new Token[] {
                        new TagToken(TokenTag.FACTORIAL),
                        new NumberToken(2.46),
                        new TagToken(TokenTag.MINUS),
                        new NumberToken(-45e-4),
                        new TagToken(TokenTag.MINUS),
                        new NumberToken(45),
                        new TagToken(TokenTag.FACTORIAL),
                        new TagToken(TokenTag.COSINE),
                },
            },
            // `Lexer` can handle an empty string.
            {
                "",
                new Token[] {},
            },
        });
    }

    @Test
    public void testLexer() throws IOException, InvalidTokenException {
        // ARRANGE
        Lexer lexer = new Lexer(inputString);
        // ACTION
        Token[] observedTokens = lexer.completeScan();
        // ASSERT
        Assert.assertTrue(Token.fuzzyArrayEquals(observedTokens, expectedTokens));
    }
}
