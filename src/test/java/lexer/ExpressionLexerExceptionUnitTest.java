package lexer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class ExpressionLexerExceptionUnitTest {

    private final String inputString;
    private final Class<IllegalLexemeException> exceptionClass;
    private final int lineNumber;
    private final int characterNumber;

    public ExpressionLexerExceptionUnitTest(
        String inputString,
        Class<IllegalLexemeException> expectedExceptionClass,
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
        // `ExpressionLexer` throws an...
        return Arrays.asList(new Object[][] {
                // `EmptyNumberException` if a number has a decimal point but neither integral nor fractional parts
                {".", EmptyNumberException.class, 1, 2},
                // `MissingIntegerException` if no integer follows a number's unsigned exponent
                {"3e", MissingIntegerException.class, 1, 3},
                // `MissingIntegerException` if no integer follows a number's signed exponent
                {"83.3e-", MissingIntegerException.class, 1, 7},
                // `LeadingZeroException` if a number has a leading zero
                {"01", LeadingZeroException.class, 1, 2},
                // `IllegalLexemeException` if 'c' is not a prefix to "cos"
                {"co4", IncompleteCosineException.class, 1, 3},
                // `IllegalLexemeException` if it encounters a character that is a prefix to no lexeme
                {"dos", IllegalCharacterException.class, 1, 1},
        });
    }

    @Test
    public void testExpressionLexerExceptions() throws IOException {
        // ARRANGE
        ExpressionLexer lexer = new ExpressionLexer(inputString);
        // ACTION
        // ASSERT
        // test for the correct exception class
        IllegalLexemeException exception = Assert.assertThrows(
            exceptionClass,
            lexer::scan
        );
        // test for the correct line and character numbers
        Assert.assertEquals(lineNumber, exception.getLineNumber());
        Assert.assertEquals(characterNumber, exception.getCharacterNumber());
    }
}
