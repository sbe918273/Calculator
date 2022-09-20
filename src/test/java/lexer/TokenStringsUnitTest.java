package lexer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class TokenStringsUnitTest {

    private final Token token;
    private final String expectedString;

    public TokenStringsUnitTest(Token token, String expectedString) {
        this.token = token;
        this.expectedString = expectedString;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        // The `toString` method is correct for a...
        return Arrays.asList(new Object[][] {
                // plus token
                {new TagToken("PLUS"), "[PLUS]"},
                // minus token
                {new TagToken("MINUS"), "[MINUS]"},
                // power token
                {new TagToken("POWER"), "[POWER]"},
                // cosine token
                {new TagToken("COSINE"), "[COSINE]"},
                // factorial token
                {new TagToken("FACTORIAL"), "[FACTORIAL]"},
                // number token
                {new NumberToken(-1.3e3), "[NUMBER] value=-1300.0"}
        });
    }

    @Test
    public void testTokenStrings() {
        // ARRANGE
        // ACTION
        String observedString = token.toString();
        // ASSERT
        Assert.assertEquals(observedString, expectedString);
    }
}
