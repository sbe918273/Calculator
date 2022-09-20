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
public class LexerExceptionUnitTest {

    private final String inputString;
    private final Class<InvalidTokenException> exceptionClass;

    public LexerExceptionUnitTest(String inputString, Class<InvalidTokenException> expectedExceptionClass) {
        this.inputString = inputString;
        this.exceptionClass = expectedExceptionClass;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        // `Lexer` throws an...
        return Arrays.asList(new Object[][] {
                // `EmptyNumberException` if a number has a decimal point but neither integral nor fractional parts
                {".", EmptyNumberException.class},
                // `MissingIntegerException` if no integer follows a number's unsigned exponent
                {"3e", MissingIntegerException.class},
                // `MissingIntegerException` if no integer follows a number's signed exponent
                {"83.3e-", MissingIntegerException.class},
                // `LeadingZeroException` if a number has a leading zero
                {"01", LeadingZeroException.class},
                // `InvalidTokenException` if 'c' is not a prefix to "cos"
                {"co4", IncompleteCosineException.class},
                // `InvalidTokenException` if it encounters a character that is a prefix to no lexeme
                {"dos", IllegalCharacterException.class}
        });
    }

    @Test
    public void testLexerExceptions() throws IOException {
        // ARRANGE
        Lexer lexer = new Lexer(inputString);
        // ACTION
        // ASSERT
        Assert.assertThrows(
            exceptionClass,
            lexer::scan
        );
    }
}
