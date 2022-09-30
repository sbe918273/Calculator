package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Lexer;
import lexer.token.ExpressionTokenTag;
import lexer.IllegalLexemeException;
import lexer.ExpressionLexer;

import parser.production.*;
import parser.symbol.ExpressionNonterminal;
import parser.symbol.ExpressionNonterminalTag;
import parser.symbol.Nonterminal;

/**
 * A class to represent an SLR parser that reads an expression (or the tokens thereof) to generate a parse tree.
 * Initialises the states and transitions of the automaton that the `Parser` super object drives.
 */
public class ExpressionParser extends Parser<ExpressionTokenTag, ExpressionNonterminalTag> {

    /**
     * Initialises this parser's lexer to be an `ExpressionLexer` reading a string.
     * @param inputString an input string
     * @throws IOException the lexer throws an IO exception.
     */
    public ExpressionParser(String inputString) throws IOException {
        this(new ExpressionLexer(inputString));
    }

    /**
     * Initialises this parser's lexer (which outputs tokens of tag type `ExpressionTokenTag`).
     * @param expressionLexer a lexer that outputs tokens of tag type `ExpressionTokenTag`
     */
    public ExpressionParser(Lexer<ExpressionTokenTag> expressionLexer) {
        super(expressionLexer);
    }

    /**
     * Initialises the parser's state by creating all the states (and the behaviour and transitions thereof) and
     * respectively pushing to the state and symbol stacks a state and symbol that initialises the automaton.
     */
    @Override
    protected void initialise() {
        // initialise the ... production
        List<Production<ExpressionTokenTag, ExpressionNonterminalTag>> productions = List.of(
                // plus
                new PlusProduction(),
                // minus
                new MinusProduction(),
                // power
                new PowerProduction(),
                // cosine
                new CosineProduction(),
                // factorial
                new FactorialProduction(),
                // number
                new NumberProduction()
        );

        // initialise the state list to be empty
        final List<State<ExpressionTokenTag, ExpressionNonterminalTag>> states = new ArrayList<>();

        // initialise the ... state's name and default error action
        // first
        states.add(new State<>(
            "I_0",
            new ExpectedOperandExceptionAction(
                "Expected operand (number or prefix operator) at start of the input."
            )
        ));
        // second
        states.add(new State<>(
            "I_1",
            new ExpectedOperatorExceptionAction("Expected operator (infix or postfix) after an expression.")
        ));
        // third
        states.add(new State<>(
            "I_2",
            new ExpectedOperandExceptionAction(
                    "Expected operand (number or prefix operator) after cosine operator."
            )
        ));
        // fourth
        states.add(new State<>(
            "I_3",
            new ExpectedOperatorExceptionAction("Expected operator (infix or postfix) after number.")
        ));
        // fifth
        states.add(new State<>(
            "I_4",
            new ExpectedOperandExceptionAction(
                "Expected operand (number or prefix operator) after plus operator."
            )
        ));
        // sixth
        states.add(new State<>(
            "I_5",
            new ExpectedOperandExceptionAction(
                "Expected operand (number or prefix operator) after minus operator."
            )
        ));
        // seventh
        states.add(new State<>(
            "I_6",
            new ExpectedOperandExceptionAction(
                "Expected operand (number or prefix operator) after power operator."
            )
        ));
        // eighth
        states.add(new State<>(
            "I_7",
            new ExpectedOperatorExceptionAction(
                "Expected operator (infix or postfix) after factorial operator."
            )
        ));
        // ninth
        states.add(new State<>(
            "I_8",
            new ExpectedOperatorExceptionAction(
                "Expected operator (infix or postfix) after cosine expression."
            )
        ));
        // tenth
        states.add(new State<>(
            "I_9",
            new ExpectedOperatorExceptionAction(
                "Expected operator (infix or postfix) after plus expression."
            )
        ));
        // eleventh
        states.add(new State<>(
            "I_10",
            new ExpectedOperatorExceptionAction(
                "Expected operator (infix or postfix) after minus expression."
            )
        ));
        // twelfth
        states.add(new State<>(
            "I_11",
            new ExpectedOperatorExceptionAction(
                "Expected operator (infix or postfix) after power expression."
            )
        ));

        // initialise the ... state's actions and next states.
        // See `table.jpg` for the corresponding SLR parsing table.

        // first
        states.get(0).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(0).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(0).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(1));

        // second
        states.get(1).putAction(ExpressionTokenTag.PLUS, new ShiftAction(states.get(4)));
        states.get(1).putAction(ExpressionTokenTag.MINUS, new ShiftAction(states.get(5)));
        states.get(1).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        states.get(1).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(1).putAction(null, new AcceptAction());

        // third
        states.get(2).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(2).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(2).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(8));

        // fourth
        states.get(3).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(5)));
        states.get(3).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(5)));
        states.get(3).putAction(ExpressionTokenTag.POWER, new ReduceAction(productions.get(5)));
        states.get(3).putAction(ExpressionTokenTag.COSINE, new ReduceAction(productions.get(5)));
        states.get(3).putAction(ExpressionTokenTag.FACTORIAL, new ReduceAction(productions.get(5)));
        states.get(3).putAction(null, new ReduceAction(productions.get(5)));

        // fifth
        states.get(4).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(4).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(4).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(9));

        // sixth
        states.get(5).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(5).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(5).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(10));

        // seventh
        states.get(6).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(6).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(6).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(11));

        // eighth
        states.get(7).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(4)));
        // The minus operator has a greater precedence than the plus operator.
        states.get(7).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(4)));
        states.get(7).putAction(ExpressionTokenTag.POWER, new ReduceAction(productions.get(4)));

        states.get(7).putAction(ExpressionTokenTag.FACTORIAL, new ReduceAction(productions.get(4)));
        states.get(7).putAction(null, new ReduceAction(productions.get(4)));

        // ninth
        // The cosine operator has a greater precedence than the plus operator.
        states.get(8).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(3)));
        // The cosine operator has a greater precedence than the minus operator.
        states.get(8).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(3)));
        // The cosine operator has a greater precedence than the power operator.
        states.get(8).putAction(ExpressionTokenTag.POWER, new ReduceAction(productions.get(3)));
        // The factorial operator has a greater precedence than the cosine operator.
        states.get(8).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(8).putAction(null, new ReduceAction(productions.get(3)));

        // tenth
        // The plus operator is left associative.
        states.get(9).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(0)));
        // The minus operator has a greater precedence than the plus operator.
        states.get(9).putAction(ExpressionTokenTag.MINUS, new ShiftAction(states.get(5)));
        // The power operator has a greater precedence than the plus operator.
        states.get(9).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        // The factorial operator has a greater precedence than the plus operator.
        states.get(9).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(9).putAction(null, new ReduceAction(productions.get(0)));

        // eleventh
        // The minus operator has a greater precedence than the plus operator.
        states.get(10).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(1)));
        // The minus operator is left associative.
        states.get(10).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(1)));
        // The power operator has a greater precedence than the minus operator.
        states.get(10).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        // The factorial operator has a greater precedence than the minus operator.
        states.get(10).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(10).putAction(null, new ReduceAction(productions.get(1)));

        // twelfth
        // The power operator has a greater precedence than the plus operator.
        states.get(11).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(2)));
        // The power operator has a greater precedence than the minus operator.
        states.get(11).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(2)));
        // The power operator is left associative.
        states.get(11).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        // The factorial operator has a greater precedence than the power operator.
        states.get(11).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(11).putAction(null, new ReduceAction(productions.get(2)));

        // initialise the driver's state
        // remove all existing states from `stateStack`
        stateStack.clear();
        // push the initial state to the stack of states
        stateStack.add(states.get(0));
        // push an initial `null` symbol to the stack of symbols
        symbolStack.add(null);
    }

    /**
     * Generates a parse tree (represented by a nonterminal) from an input string.
     * We use an `ExpressionLexer` as the lexer.
     * @param inputString an input string
     * @return the resulting parse tree
     * @throws IOException the lexer throws an IO exception
     * @throws IllegalLexemeException the lexer throws an `IllegalLexemeException`
     * @throws IllegalTokenException the lexer throws an `IllegalTokenException`
     */
    public static ExpressionNonterminal parse(String inputString) throws
        IOException,
        IllegalLexemeException,
        IllegalTokenException
    {
        // create an `ExpressionParser` to parse the input string
        ExpressionParser expressionParser = new ExpressionParser(inputString);
        // execute the `ExpressionParser`'s SLR parsing algorithm and retrieve the resulting parse tree
        Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> nonterminal = expressionParser.run();
        // assert that the nonterminal is an `ExpressionNonterminal` (as ensured by the productions' return types)
        if (nonterminal instanceof ExpressionNonterminal expressionNonterminal) {
            return expressionNonterminal;
        }
        throw new IllegalStateException("[ExpressionParser:parser] Root symbol must be an `ExpressionNonterminal`.");
    }
}

