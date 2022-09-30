package lexer;

import lexer.token.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class ExpressionLexerIntegrationTest {

    private final String inputString;
    private final List<Token<ExpressionTokenTag>> expectedTokens;

    public ExpressionLexerIntegrationTest(String inputString, List<Token<ExpressionTokenTag>> expectedTokens) {
        this.inputString = inputString;
        this.expectedTokens = expectedTokens;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            {
                "cos90!+\t3.45e-9\n--87^4 +.0001!4.e+1",
                List.of(
                    new CosineToken(),
                    new NumberToken(90),
                    new FactorialToken(),
                    new PlusToken(),
                    new NumberToken(3.45e-9),
                    new MinusToken(),
                    new NumberToken(-87),
                    new PowerToken(),
                    new NumberToken(4),
                    new PlusToken(),
                    new NumberToken(.0001),
                    new FactorialToken(),
                    new NumberToken(4.e+1)
                ),
            },
            {
                "!+2.46--45e-4-45!cos",
                List.of(
                        new FactorialToken(),
                        new NumberToken(2.46),
                        new MinusToken(),
                        new NumberToken(-45e-4),
                        new MinusToken(),
                        new NumberToken(45),
                        new FactorialToken(),
                        new CosineToken()
                ),
            },
            // `ExpressionLexer` can handle an empty string.
            {
                "",
                List.of(),
            },
        });
    }

    @Test
    public void testExpressionLexer() throws IOException, IllegalLexemeException {
        // ARRANGE
        ExpressionLexer expressionLexer = new ExpressionLexer(inputString);
        // ACTION
        List<Token<ExpressionTokenTag>> observedTokens = expressionLexer.completeScan();
        // ASSERT
        Assert.assertTrue(Token.fuzzyListEquals(observedTokens, expectedTokens));
    }
}
