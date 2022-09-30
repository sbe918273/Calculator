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

public class ExpressionParser extends Parser<ExpressionTokenTag, ExpressionNonterminalTag> {

    private static final List<Production<ExpressionTokenTag, ExpressionNonterminalTag>> productions;

    static {
        productions = List.of(
            // E -> E + E
            new PlusProduction(),
            // E -> E - E
            new MinusProduction(),
            // E -> E ^ E
            new PowerProduction(),
            // E -> cos E
            new CosineProduction(),
            // E -> E!
            new FactorialProduction(),
            // E -> number
            new NumberProduction()
        );
    }

    public ExpressionParser(String inputString) throws IOException {
        this(new ExpressionLexer(inputString));
    }

    public ExpressionParser(Lexer<ExpressionTokenTag> expressionLexer) {
        super(expressionLexer);
    }

    @Override
    protected void initialise() {
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
        states.get(7).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(4)));
        states.get(7).putAction(ExpressionTokenTag.POWER, new ReduceAction(productions.get(4)));
        states.get(7).putAction(ExpressionTokenTag.FACTORIAL, new ReduceAction(productions.get(4)));
        states.get(7).putAction(null, new ReduceAction(productions.get(4)));

        // ninth
        states.get(8).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(3)));
        states.get(8).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(3)));
        states.get(8).putAction(ExpressionTokenTag.POWER, new ReduceAction(productions.get(3)));
        states.get(8).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(8).putAction(null, new ReduceAction(productions.get(3)));

        // tenth
        states.get(9).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(0)));
        states.get(9).putAction(ExpressionTokenTag.MINUS, new ShiftAction(states.get(5)));
        states.get(9).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        states.get(9).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(9).putAction(null, new ReduceAction(productions.get(0)));

        // eleventh
        states.get(10).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(1)));
        states.get(10).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(1)));
        states.get(10).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        states.get(10).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(10).putAction(null, new ReduceAction(productions.get(1)));

        // twelfth
        states.get(11).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(2)));
        states.get(11).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(2)));
        states.get(11).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        states.get(11).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(11).putAction(null, new ReduceAction(productions.get(2)));

        // remove all existing states from `stateStack`
        stateStack.clear();
        // push the initial state to the stack of states
        stateStack.add(states.get(0));
        symbolStack.add(null);
    }

    public static ExpressionNonterminal parse(String inputString) throws
        IOException,
        IllegalLexemeException,
        IllegalTokenException
    {
        ExpressionParser expressionParser = new ExpressionParser(inputString);
        Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> nonterminal = expressionParser.run();
        if (nonterminal instanceof ExpressionNonterminal expressionNonterminal) {
            return expressionNonterminal;
        }
        throw new IllegalStateException("[ExpressionParser:parser] Root symbol must be an `ExpressionNonterminal`.");
    }
}

