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
public class ExpressionLexerMultipleTokensUnitTest {

    private final String inputString;
    private final Token<ExpressionTokenTag>[] expectedTokens;

    public ExpressionLexerMultipleTokensUnitTest(String inputString, Token<ExpressionTokenTag>[] expectedTokens) {
        this.inputString = inputString;
        this.expectedTokens = expectedTokens;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                // `ExpressionLexer` skips whitespace.
                {
                    " cos\r\n4\t^",
                    new Token[] {
                        new CosineToken(),
                        new NumberToken(4),
                        new PowerToken(),
                    }
                },
                // `ExpressionLexer` attempts to interpret the first sign after a number as an operand.
                {
                    "30e-1+6",
                    new Token[] {
                        new NumberToken(30e-1),
                        new PlusToken(),
                        new NumberToken(6),
                    }
                },
                // `ExpressionLexer` can interpret the first sign after an operand as being an operand.
                {
                    "3+^4",
                    new Token[] {
                        new NumberToken(3),
                        new PlusToken(),
                        new PowerToken(),
                        new NumberToken(4),
                    },
                },
                // `ExpressionLexer` attempts to interpret the last sign after a number to be that of a number.
                {
                    "10e-1+-2",
                    new Token[] {
                        new NumberToken(10e-1),
                        new PlusToken(),
                        new NumberToken(-2),
                    },
                },
        });
    }

    @Test
    public void testExpressionLexerMultipleTokens() throws IOException, InvalidTokenException {
        // ARRANGE
        ExpressionLexer expressionLexer = new ExpressionLexer(inputString);
        // ACTION
        Token<ExpressionTokenTag>[] observedTokens = expressionLexer.completeScan();
        // ASSERT
        Assert.assertTrue(Token.fuzzyArrayEquals(observedTokens, expectedTokens));
    }
}
