package parser;

import lexer.IllegalLexemeException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import parser.symbol.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class ExpressionParserIntegrationTest {

    private final static double TOLERANCE = 1e-6;
    private final String inputString;
    private final double expectedValue;
    private final ExpressionNonterminal expectedNonterminal;

    public ExpressionParserIntegrationTest(
            String inputString,
            double expectedValue,
            ExpressionNonterminal expectedNonterminal
    ) {
        this.inputString = inputString;
        this.expectedValue = expectedValue;
        this.expectedNonterminal = expectedNonterminal;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            // `ExpressionParser` correctly parses a number.
            {
                "3.06e+2\n^-.89+5!-cos3!\n",
                Math.pow(3.06e+2, -.89) + FactorialNonterminal.factorial(5) -
                        Math.cos(FactorialNonterminal.factorial(3)),
                new PlusNonterminal(
                    new PowerNonterminal(
                         new NumberNonterminal(3.06e+2),
                         new NumberNonterminal(-.89)
                    ),
                    new MinusNonterminal(
                        new FactorialNonterminal(new NumberNonterminal(5)),
                        new CosineNonterminal(new FactorialNonterminal(new NumberNonterminal(3)))
                    )
                )
            },
            {
                ".008e+2^ -2^ 3+0! ",
                Math.pow(.008e+2, Math.pow(-2, 3)) + FactorialNonterminal.factorial(0),
                new PlusNonterminal(
                    new PowerNonterminal(
                        new NumberNonterminal(.008e+2),
                        new PowerNonterminal(
                            new NumberNonterminal(-2),
                            new NumberNonterminal(3)
                        )
                    ),
                    new FactorialNonterminal(new NumberNonterminal(0))
                )
            },
            {
                "2. + cos89-34- 54 \n+ 4!",
                2. + Math.cos(89) - 34 - 54 + FactorialNonterminal.factorial(4),
                new PlusNonterminal(
                    new PlusNonterminal(
                        new NumberNonterminal(2.),
                        new MinusNonterminal(
                            new MinusNonterminal(
                                new CosineNonterminal(new NumberNonterminal(89)),
                                new NumberNonterminal(34)
                            ),
                            new NumberNonterminal(54)
                        )
                    ),
                    new FactorialNonterminal(new NumberNonterminal(4))
                )
            }
        });
    }

    @Test
    public void testExpressionParser() throws IOException, IllegalLexemeException, IllegalTokenException {
        // ARRANGE
        // ACTION
        ExpressionNonterminal observedNonterminal = ExpressionParser.parse(inputString);
        // ASSERT
        // test for the correct value
        Assert.assertEquals(expectedValue, observedNonterminal.getValue(), expectedValue * TOLERANCE);
        // test for the correct parse tree
        Assert.assertTrue(observedNonterminal.fuzzyEquals(expectedNonterminal));
    }
}
