package parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class ExpressionParserExceptionUnitTest {

    private final String inputString;
    private final Class<IllegalTokenException> exceptionClass;
    private final int lineNumber;
    private final int characterNumber;

    public ExpressionParserExceptionUnitTest(
            String inputString,
            Class<IllegalTokenException> expectedExceptionClass,
            int lineNumber,
            int characterNumber
    ) {
        this.inputString = inputString;
        this.exceptionClass = expectedExceptionClass;
        this.lineNumber = lineNumber;
        this.characterNumber = characterNumber;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        // `ExpressionParser` throws an...
        return Arrays.asList(new Object[][] {
                // `ExpectedOperandException` at the start of the input
                {"^4", ExpectedOperandException.class, 1, 2},
                // `ExpectedOperandException` at the end of the input
                {"5.2e-3+", ExpectedOperandException.class, 1, 8},
                // `ExpectedOperandException` after a cosine operator
                {"cos!", ExpectedOperandException.class, 1, 5},
                // `ExpectedOperatorException` after a number
                {"2.445\n6e-10", ExpectedOperatorException.class, 2, 6},
                // `ExpectedOperatorException` after a plus operator
                {"5+^90", ExpectedOperandException.class, 1, 4},
                // `ExpectedOperatorException` after a minus operator
                {"cos5-", ExpectedOperandException.class, 1, 6},
                // `ExpectedOperandException` after a power operator
                {"72^!", ExpectedOperandException.class, 1, 5},
                // `ExpectedOperatorException` after a factorial operator
                {"75!cos2", ExpectedOperatorException.class, 1, 7},
                // `ExpectedOperatorException` after a cosine expression
                {"cos14.0004e-4\n\n5", ExpectedOperatorException.class, 3, 2},
                // `ExpectedOperatorException` after a plus expression
                {"39.0+3 .8", ExpectedOperatorException.class, 1, 10},
                // `ExpectedOperatorException` after a minus expression
                {"5.3-.3\ncos1", ExpectedOperatorException.class, 2, 4},
                // `ExpectedOperatorException` after a power expression
                {"400^2 4\n", ExpectedOperatorException.class, 1, 8},
        });
    }

    @Test
    public void testExpressionLexerExceptions() {
        // ARRANGE
        // ACTION
        // ASSERT
        // test for the correct exception class
        IllegalTokenException exception = Assert.assertThrows(
            exceptionClass,
            () -> ExpressionParser.parse(inputString)
        );
        // test for the correct line and character numbers
        Assert.assertEquals(lineNumber, exception.getLineNumber());
        Assert.assertEquals(characterNumber, exception.getCharacterNumber());
    }
}
