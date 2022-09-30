package parser;

import lexer.IllegalLexemeException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import parser.symbol.*;

import lexer.Lexer;
import lexer.token.*;
import main.UnpositionedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A class to mock `ExpressionLexer` by outputting provided tokens.
 */
class MockExpressionLexer implements Lexer<ExpressionTokenTag> {

    // the queue of tokens to output
    private final Queue<Token<ExpressionTokenTag>> tokens;

    /**
     * Initialises the queue of tokens to output.
     * @param tokens a token list
     */
    public MockExpressionLexer(List<Token<ExpressionTokenTag>> tokens) {
        this.tokens = new LinkedList<>(tokens);
    }

    /**
     * This lexer does not have a line number.
     */
    @Override
    public int getLineNumber() {
        throw new UnpositionedException(
                "MockExpressionLexer",
                "getLineNumber",
                "Cannot retrieve line number from lexer directly outputting tokens."
        );
    }

    /**
     * This lexer does not have a character number.
     */
    @Override
    public int getCharacterNumber() {
        throw new UnpositionedException(
                "MockExpressionLexer",
                "getCharacterNumber",
                "Cannot retrieve character number from lexer directly outputting tokens."
        );
    }

    /**
     * @return the next token in the queue (`null` if the queue is exhausted)
     */
    @Override
    public Token<ExpressionTokenTag> scan() {
        return tokens.poll();
    }
}

@RunWith(Parameterized.class)
public class ExpressionParserUnitTest {

    private final List<Token<ExpressionTokenTag>> tokens;
    private final ExpressionNonterminal expectedNonterminal;

    public ExpressionParserUnitTest(
        List<Token<ExpressionTokenTag>> tokens,
        ExpressionNonterminal expectedNonterminal
    ) {
        this.tokens = tokens;
        this.expectedNonterminal = expectedNonterminal;
    }

    @Parameters
    public static List<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            // `ExpressionParser` correctly parses a number.
            {
                List.of(
                    new NumberToken(30)
                ),
                new NumberNonterminal(30),
            },
            // `ExpressionParser` correctly parses a(n) ... expression exactly containing two numbers.
            // sum
            {
                List.of(
                    new NumberToken(4),
                    new PlusToken(),
                    new NumberToken(2.3)
                ),
                new PlusNonterminal(
                    new NumberNonterminal(4),
                    new NumberNonterminal(2.3)
                ),
            },
            // subtraction
            {
                List.of(
                    new NumberToken(40),
                    new MinusToken(),
                    new NumberToken(75.5)
                ),
                new MinusNonterminal(
                        new NumberNonterminal(40),
                        new NumberNonterminal(75.5)
                ),
            },
            // exponentiation
            {
                List.of(
                    new NumberToken(32.1),
                    new PowerToken(),
                    new NumberToken(0.64)
                ),
                new PowerNonterminal(
                    new NumberNonterminal(32.1),
                    new NumberNonterminal(0.64)
                ),
            },
            // `ExpressionParser` correctly parses a(n) ... expression exactly containing one number.
            // cosine
            {
                List.of(
                    new CosineToken(),
                    new NumberToken(28)
                ),
                new CosineNonterminal(new NumberNonterminal(28)),
            },
            {
                List.of(
                    new NumberToken(9),
                    new FactorialToken()
                ),
                new FactorialNonterminal(new NumberNonterminal(9)),
            },
            // `ExpressionParser` observes the ... operator
            // left associativity of the plus
            {
                List.of(
                    new NumberToken(9.5),
                    new PlusToken(),
                    new NumberToken(-2.003),
                    new PlusToken(),
                    new NumberToken(100)
                ),
                new PlusNonterminal(
                    new PlusNonterminal(
                        new NumberNonterminal(9.5),
                        new NumberNonterminal(-2.003)
                    ),
                    new NumberNonterminal(100)
                ),
            },
            // left associativity of the minus
            {
                List.of(
                    new NumberToken(3.95),
                    new MinusToken(),
                    new NumberToken(4.7),
                    new MinusToken(),
                    new NumberToken(8.9)
                ),
                new MinusNonterminal(
                    new MinusNonterminal(
                        new NumberNonterminal(3.95),
                        new NumberNonterminal(4.7)
                    ),
                    new NumberNonterminal(8.9)
                ),
            },
            // right associativity of the power
            {
                List.of(
                    new NumberToken(2.8),
                    new PowerToken(),
                    new NumberToken(1.3),
                    new PowerToken(),
                    new NumberToken(0.7)
                ),
                new PowerNonterminal(
                    new NumberNonterminal(2.8),
                    new PowerNonterminal(
                        new NumberNonterminal(1.3),
                        new NumberNonterminal(0.7)
                    )
                ),
            },
            // `ExpressionParser` observes the relative precedences of the ... operators
            // plus and minus
            {
                List.of(
                    new NumberToken(0.2),
                    new PlusToken(),
                    new NumberToken(0.006),
                    new MinusToken(),
                    new NumberToken(-1.4)
                ),
                new PlusNonterminal(
                    new NumberNonterminal(0.2),
                    new MinusNonterminal(
                        new NumberNonterminal(0.006),
                        new NumberNonterminal(-1.4)
                    )
                ),
            },
            // plus and power
            {
                List.of(
                    new NumberToken(3.6),
                    new PowerToken(),
                    new NumberToken(-1.2),
                    new PlusToken(),
                    new NumberToken(72.4)
                ),
                new PlusNonterminal(
                    new PowerNonterminal(
                        new NumberNonterminal(3.6),
                        new NumberNonterminal(-1.2)
                    ),
                    new NumberNonterminal(72.4)
                ),
            },
            // plus and cosine
            {
                List.of(
                    new CosineToken(),
                    new NumberToken(21),
                    new PlusToken(),
                    new NumberToken(9.3)
                ),
                new PlusNonterminal(
                    new CosineNonterminal(new NumberNonterminal(21)),
                    new NumberNonterminal(9.3)
                ),
            },
            // plus and factorial
            {
                List.of(
                    new NumberToken(5),
                    new PlusToken(),
                    new NumberToken(8),
                    new FactorialToken()
                ),
                new PlusNonterminal(
                    new NumberNonterminal(5),
                    new FactorialNonterminal(new NumberNonterminal(8))
                ),
            },
            // minus and power
            {
                List.of(
                    new NumberToken(45),
                    new MinusToken(),
                    new NumberToken(2),
                    new PowerToken(),
                    new NumberToken(7)
                ),
                new MinusNonterminal(
                    new NumberNonterminal(45),
                    new PowerNonterminal(
                        new NumberNonterminal(2),
                        new NumberNonterminal(7)
                    )
                ),
            },
            // minus and cosine
            {
                List.of(
                    new CosineToken(),
                    new NumberToken(62),
                    new MinusToken(),
                    new NumberToken(-0.027)
                ),
                new MinusNonterminal(
                    new CosineNonterminal(new NumberNonterminal(62)),
                    new NumberNonterminal(-0.027)
                ),
            },
            // minus and factorial
            {
                List.of(
                    new NumberToken(93),
                    new MinusToken(),
                    new NumberToken(4),
                    new FactorialToken()
                ),
                new MinusNonterminal(
                    new NumberNonterminal(93),
                    new FactorialNonterminal(new NumberNonterminal(4))
                ),
            },
            // power and cosine
            {
                List.of(
                    new CosineToken(),
                    new NumberToken(4.107),
                    new PowerToken(),
                    new NumberToken(2)
                ),
                new PowerNonterminal(
                    new CosineNonterminal(new NumberNonterminal(4.107)),
                    new NumberNonterminal(2)
                ),
            },
            // power and factorial
            {
                List.of(
                    new NumberToken(3.54),
                    new PowerToken(),
                    new NumberToken(2),
                    new FactorialToken()
                ),
                new PowerNonterminal(
                    new NumberNonterminal(3.54),
                    new FactorialNonterminal(new NumberNonterminal(2))
                ),
            },
            // cosine and factorial
            {
                List.of(
                    new CosineToken(),
                    new NumberToken(5),
                    new FactorialToken()
                ),
                new CosineNonterminal(new FactorialNonterminal(new NumberNonterminal(5)))
            },
        });
    }

    @Test
    public void testExpressionParser() throws IOException, IllegalLexemeException, IllegalTokenException {
        // ARRANGE
        ExpressionParser expressionParser = new ExpressionParser(new MockExpressionLexer(tokens));
        // ACTION
        Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> nonterminal = expressionParser.run();
        // ASSERT
        Assert.assertTrue(
            (nonterminal instanceof ExpressionNonterminal observedNonterminal) &&
            observedNonterminal.fuzzyEquals(expectedNonterminal)
        );
    }
}
