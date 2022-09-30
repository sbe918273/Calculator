package lexer;

import lexer.token.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class ExpressionLexerUnitTest {

    private final String inputString;
    private final List<Token<ExpressionTokenTag>> expectedTokens;

    public ExpressionLexerUnitTest(String inputString, List<Token<ExpressionTokenTag>>  expectedTokens) {
        this.inputString = inputString;
        this.expectedTokens = expectedTokens;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            // `ExpressionLexer` correctly recognises a(n) ...
            // plus
            {
                "+",
                List.of(new PlusToken())
            },
            // minus
            {
                "-",
                List.of(new MinusToken())
            },
            // power
            {
                "^",
                List.of(new PowerToken())
            },
            // cosine
            {
                "cos",
                List.of(new CosineToken())
            },
            // factorial
            {
                "!",
                List.of(new FactorialToken())
            },
            // unsigned integer
            {
                "56",
                List.of(new NumberToken(56))
            },
            // unsigned double
            {
                "42.3",
                List.of(new NumberToken(42.3))
            },
            // unsigned integer with an unsigned exponent
            {
                "3e4",
                List.of(new NumberToken(3e4))},
            // unsigned integer with a signed exponent
            {
                "71e-5",
                List.of(new NumberToken(71e-5))
            },
            // unsigned double with an unsigned exponent
            {
                "7.08e7",
                List.of(new NumberToken(7.08e7))
            },
            // unsigned double with a signed exponent
            {
                "2.001e+3",
                List.of(new NumberToken(2.001e+3))
            },
            // signed integer
            {
                "-9",
                List.of(new NumberToken(-9))
            },
            // signed double
            {
                "-11.111",
                List.of(new NumberToken(-11.111))
            },
            // signed integer with an unsigned exponent
            {
                "+3.5e8",
                List.of(new NumberToken(+3.5e8))
            },
            // signed integer with a signed exponent
            {
                "-633e-2",
                List.of(new NumberToken(-633e-2))
            },
            // signed double with an unsigned exponent
            {
                "-40.5e2",
                List.of(new NumberToken(-40.5e2))
            },
            // signed double with a signed exponent
            {
                "+204.345e+0",
                List.of(new NumberToken(+204.345e+0))
            },
            // zero
            {
                "0",
                List.of(new NumberToken(0))
            },
            // an unsigned double with a missing integral part
            {
                ".03",
                List.of(new NumberToken(.03))
            },
            // a signed double with a missing integral part
            {
                "-.9999e+12",
                List.of(new NumberToken(-.9999e+12))
            },
            // a double with a missing fractional part
            {
                "4.",
                List.of(new NumberToken(4.))
            },
            // a double with an exponent but a missing fractional part
            {
                "67.e-3",
                List.of(new NumberToken(67.e-3))
            },
            // `ExpressionLexer` correctly...
            // skips whitespace
            {
                " cos\r\n4\t^",
                List.of(
                    new CosineToken(),
                    new NumberToken(4),
                    new PowerToken()
                ),
            },
            // attempts to interpret the first sign after a number as an operand.
            {
                "30e-1+6",
                List.of(
                    new NumberToken(30e-1),
                    new PlusToken(),
                    new NumberToken(6)
                )
            },
            // interprets the first sign after an operand as being an operand.
            {
                "3+^4",
                List.of(
                    new NumberToken(3),
                    new PlusToken(),
                    new PowerToken(),
                    new NumberToken(4)
                ),
            },
            // attempts to interpret the last sign after a number to be that of a number.
            {
                "10e-1+-2",
                List.of(
                    new NumberToken(10e-1),
                    new PlusToken(),
                    new NumberToken(-2)
                ),
            },
        });
    }

    @Test
    public void testExpressionLexerTokens() throws IOException, IllegalLexemeException {
        // ARRANGE
        ExpressionLexer expressionLexer = new ExpressionLexer(inputString);
        // ACTION
        List<Token<ExpressionTokenTag>> observedTokens = expressionLexer.completeScan();
        // ASSERT
        Assert.assertTrue(Token.fuzzyListEquals(observedTokens, expectedTokens));
    }
}
