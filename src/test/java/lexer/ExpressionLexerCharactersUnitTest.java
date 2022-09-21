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
    private final TagToken expectedToken;

    public ExpressionLexerCharactersUnitTest(String lexeme, TagToken expectedToken) {
        this.lexeme = lexeme;
        this.expectedToken = expectedToken;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        // `ExpressionLexer` correctly recognises a ... token.
        return Arrays.asList(new Object[][] {
            // plus
            {"+", new TagToken(ExpressionTokenTag.PLUS)},
            // minus
            {"-", new TagToken(ExpressionTokenTag.MINUS)},
            // power
            {"^", new TagToken(ExpressionTokenTag.POWER)},
            // cosine
            {"cos", new TagToken(ExpressionTokenTag.COSINE)},
            // factorial
            {"!", new TagToken(ExpressionTokenTag.FACTORIAL)},
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
