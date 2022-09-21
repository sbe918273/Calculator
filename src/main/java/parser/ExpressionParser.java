package parser;

import lexer.ExpressionTokenTag;
import lexer.InvalidTokenException;
import lexer.ExpressionLexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpressionParser extends Parser<ExpressionTokenTag, ExpressionNonterminalTag> {

    private static final List<Production<ExpressionTokenTag, ExpressionNonterminalTag>> productions;
    private static final int stateCount = 12;

    static {

        Production<ExpressionTokenTag, ExpressionNonterminalTag> plusProduction = new Production<>(
            "E -> E + E",
                ExpressionNonterminalTag.EXPRESSION,
                3
        ) {
            @Override
            public Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> createNonterminal(
                    List<Symbol<ExpressionTokenTag, ExpressionNonterminalTag>> children
            ) {
                // assert left is nonterminal
                // assert middle is plus
                // assert right is nonterminal
                return super.createNonterminal(children);
            }
        };

        productions = List.of(
            // E -> E + E
            new Production<>("0", ExpressionNonterminalTag.EXPRESSION, 3),
            // E -> E - E
            new Production<>("1", ExpressionNonterminalTag.EXPRESSION, 3),
            // E -> E ^ E
            new Production<>("2", ExpressionNonterminalTag.EXPRESSION, 3),
            // E -> cos E
            new Production<>("3", ExpressionNonterminalTag.EXPRESSION, 2),
            // E -> E!
            new Production<>("4", ExpressionNonterminalTag.EXPRESSION, 2),
            // E -> number
            new Production<>("5", ExpressionNonterminalTag.EXPRESSION, 1)
        );
    }

    public ExpressionParser(String inputString) throws IOException {
        this(new ExpressionLexer(inputString));
    }

    public ExpressionParser(ExpressionLexer expressionLexer) {
        super(expressionLexer);
    }

    @Override
    protected void initialise() {
        final List<State<ExpressionTokenTag, ExpressionNonterminalTag>> states = new ArrayList<>();
        for (int index = 0; index < stateCount; index++) {
            states.add(new State<>(Integer.toString(index)));
        }

        // first state
        states.get(0).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(0).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(0).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(1));

        // second state
        states.get(1).putAction(ExpressionTokenTag.PLUS, new ShiftAction(states.get(4)));
        states.get(1).putAction(ExpressionTokenTag.MINUS, new ShiftAction(states.get(5)));
        states.get(1).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        states.get(1).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(1).putAction(null, new AcceptAction());

        // third state
        states.get(2).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(2).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(2).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(8));

        // fourth state
        states.get(3).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(5)));
        states.get(3).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(5)));
        states.get(3).putAction(ExpressionTokenTag.COSINE, new ReduceAction(productions.get(5)));
        states.get(3).putAction(ExpressionTokenTag.FACTORIAL, new ReduceAction(productions.get(5)));
        states.get(3).putAction(null, new ReduceAction(productions.get(5)));

        // fifth state
        states.get(4).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(4).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(4).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(9));

        // sixth state
        states.get(5).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(5).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(5).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(10));

        // seventh state
        states.get(6).putAction(ExpressionTokenTag.COSINE, new ShiftAction(states.get(2)));
        states.get(6).putAction(ExpressionTokenTag.NUMBER, new ShiftAction(states.get(3)));
        states.get(6).putNextState(ExpressionNonterminalTag.EXPRESSION, states.get(11));

        // eighth state
        states.get(7).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(4)));
        states.get(7).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(4)));
        states.get(7).putAction(ExpressionTokenTag.POWER, new ReduceAction(productions.get(4)));
        states.get(7).putAction(ExpressionTokenTag.FACTORIAL, new ReduceAction(productions.get(4)));
        states.get(7).putAction(null, new ReduceAction(productions.get(4)));

        // ninth state
        states.get(8).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(3)));
        states.get(8).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(3)));
        states.get(8).putAction(ExpressionTokenTag.POWER, new ReduceAction(productions.get(3)));
        states.get(8).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(8).putAction(null, new ReduceAction(productions.get(3)));

        // tenth state
        states.get(9).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(0)));
        states.get(9).putAction(ExpressionTokenTag.MINUS, new ShiftAction(states.get(5)));
        states.get(9).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        states.get(9).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(9).putAction(null, new ReduceAction(productions.get(0)));

        // eleventh state
        states.get(10).putAction(ExpressionTokenTag.PLUS, new ReduceAction(productions.get(1)));
        states.get(10).putAction(ExpressionTokenTag.MINUS, new ReduceAction(productions.get(1)));
        states.get(10).putAction(ExpressionTokenTag.POWER, new ShiftAction(states.get(6)));
        states.get(10).putAction(ExpressionTokenTag.FACTORIAL, new ShiftAction(states.get(7)));
        states.get(10).putAction(null, new ReduceAction(productions.get(2)));

        // twelfth state
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

    public static ExpressionNonterminal parse(String inputString) throws IOException, InvalidTokenException {
        ExpressionParser expressionParser = new ExpressionParser(inputString);
        Nonterminal<ExpressionTokenTag, ExpressionNonterminalTag> nonterminal = expressionParser.run();
        if (nonterminal instanceof ExpressionNonterminal expressionNonterminal) {
            return expressionNonterminal;
        }
        throw new IllegalStateException("[ExpressionParser:parser] Root symbol must be an `ExpressionNonterminal`.");
    }
}

