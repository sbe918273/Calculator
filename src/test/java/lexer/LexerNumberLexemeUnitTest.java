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
public class LexerNumberLexemeUnitTest {

    private final String lexeme;
    private final NumberToken expectedToken;

    public LexerNumberLexemeUnitTest(String lexeme, NumberToken expectedToken) {
        this.lexeme = lexeme;
        this.expectedToken = expectedToken;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        // `Lexer` correctly recognises a(n)...
        return Arrays.asList(new Object[][] {
            // unsigned integer
            {"56", new NumberToken(56)},
            // unsigned double
            {"42.3", new NumberToken(42.3)},
            // unsigned integer with an unsigned exponent
            {"3e4", new NumberToken(3e4)},
            // unsigned integer with a signed exponent
            {"71e-5", new NumberToken(71e-5)},
            // unsigned double with an unsigned exponent
            {"7.08e7", new NumberToken(7.08e7)},
            // unsigned double with a signed exponent
            {"2.001e+3", new NumberToken(2.001e+3)},
            // signed integer
            {"-9", new NumberToken(-9)},
            // signed double
            {"-11.111", new NumberToken(-11.111)},
            // signed integer with an unsigned exponent
            {"+3.5e8", new NumberToken(+3.5e8)},
            // signed integer with a signed exponent
            {"-633e-2", new NumberToken(-633e-2)},
            // signed double with an unsigned exponent
            {"-40.5e2", new NumberToken(-40.5e2)},
            // signed double with a signed exponent
            {"+204.345e+0", new NumberToken(+204.345e+0)},
            // zero
            {"0", new NumberToken(0)},
            // unsigned double with a missing integral part
            {".03", new NumberToken(.03)},
            // signed double with a missing integral part
            {"-.9999e+12", new NumberToken(-.9999e+12)},
            // double with a missing fractional part
            {"4.", new NumberToken(4.)},
            // double with an exponent but a missing fractional part
            {"67.e-3", new NumberToken(67.e-3)},
        });
    }

    @Test
    public void testLexerNumberLexemes() throws IOException, InvalidTokenException {
        // ARRANGE
        Lexer lexer = new Lexer(lexeme);
        // ACTION
        Token observedToken = lexer.scan();
        // ASSERT
        Assert.assertTrue(expectedToken.fuzzyEquals(observedToken));
        // assert that the lexer recognises no additional tokens
        Assert.assertNull(lexer.scan());
    }
}
