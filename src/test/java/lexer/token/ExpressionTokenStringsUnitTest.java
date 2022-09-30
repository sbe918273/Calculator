package lexer.token;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class ExpressionTokenStringsUnitTest {

    private final Token<ExpressionTokenTag> token;
    private final String expectedString;

    public ExpressionTokenStringsUnitTest(Token<ExpressionTokenTag> token, String expectedString) {
        this.token = token;
        this.expectedString = expectedString;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        // The `toString` method is correct for a...
        return Arrays.asList(new Object[][] {
                // plus token
                {new PlusToken(), "[PLUS]"},
                // minus token
                {new MinusToken(), "[MINUS]"},
                // power token
                {new PowerToken(), "[POWER]"},
                // cosine token
                {new CosineToken(), "[COSINE]"},
                // factorial token
                {new FactorialToken(), "[FACTORIAL]"},
                // number token
                {new NumberToken(-1.3e3), "[NUMBER] value=-1300.0"}
        });
    }

    @Test
    public void testExpressionTokenStrings() {
        // ARRANGE
        // ACTION
        String observedString = token.toString();
        // ASSERT
        Assert.assertEquals(observedString, expectedString);
    }
}
