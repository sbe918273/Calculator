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
public class LexerCharacterLexemeUnitTest {

    private final String lexeme;
    private final TagToken expectedToken;

    public LexerCharacterLexemeUnitTest(String lexeme, TagToken expectedToken) {
        this.lexeme = lexeme;
        this.expectedToken = expectedToken;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        // `Lexer` correctly recognises a ... token.
        return Arrays.asList(new Object[][] {
            // plus
            {"+", new TagToken("PLUS")},
            // minus
            {"-", new TagToken("MINUS")},
            // power
            {"^", new TagToken("POWER")},
            // cosine
            {"cos", new TagToken("COSINE")},
            // factorial
            {"!", new TagToken("FACTORIAL")},
        });
    }

    @Test
    public void testLexerCharacterLexemes() throws IOException, InvalidTokenException {
        // ARRANGE
        Lexer lexer = new Lexer(lexeme);
        // ACTION
        Token observedToken = lexer.scan();
        // ASSERT
        Assert.assertEquals(observedToken, expectedToken);
        // assert that the lexer recognises no additional tokens
        Assert.assertNull(lexer.scan());
    }
}
