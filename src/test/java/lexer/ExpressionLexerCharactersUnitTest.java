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
public class ExpressionLexerCharactersUnitTest {

    private final String lexeme;
    private final Token<ExpressionTokenTag> expectedToken;

    public ExpressionLexerCharactersUnitTest(String lexeme, Token<ExpressionTokenTag> expectedToken) {
        this.lexeme = lexeme;
        this.expectedToken = expectedToken;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        // `ExpressionLexer` correctly recognises a ... token.
        return Arrays.asList(new Object[][] {
            // plus
            {"+", new PlusToken()},
            // minus
            {"-", new MinusToken()},
            // power
            {"^", new PowerToken()},
            // cosine
            {"cos", new CosineToken()},
            // factorial
            {"!", new FactorialToken()},
        });
    }

    @Test
    public void testExpressionLexerCharacters() throws IOException, InvalidTokenException {
        // ARRANGE
        ExpressionLexer expressionLexer = new ExpressionLexer(lexeme);
        // ACTION
        Token<ExpressionTokenTag> observedToken = expressionLexer.scan();
        // ASSERT
        Assert.assertEquals(observedToken, expectedToken);
        // assert that the lexer recognises no additional tokens
        Assert.assertNull(expressionLexer.scan());
    }
}
