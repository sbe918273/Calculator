package parser;

import lexer.InvalidTokenException;
import lexer.Lexer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

enum NonterminalTag {
    EXPRESSION,
}

enum TerminalTag {
    PLUS,
    MINUS,
    POWER,
    COSINE,
    FACTORIAL,
    NUMBER,
}

enum

public class ExpressionParser extends Parser<TerminalTag, NonterminalTag> {

    private final List<State> states;
    private static final List<Production<NonterminalTag>> productions;
    private static final int stateCount = 12;
    private boolean firstInitialisation = true;

    static {
        productions = List.of(
            // E -> E + E
            new Production<>(NonterminalTag.EXPRESSION, 3),
            // E -> E - E
            new Production<>(NonterminalTag.EXPRESSION, 3),
            // E -> E ^ E
            new Production<>(NonterminalTag.EXPRESSION, 3),
            // E -> cos E
            new Production<>(NonterminalTag.EXPRESSION, 2),
            // E -> E!
            new Production<>(NonterminalTag.EXPRESSION, 2),
            // E -> number
            new Production<>(NonterminalTag.EXPRESSION, 1)
        );
    }

    public ExpressionParser(String inputString) throws IOException {
        this(new Lexer(inputString));
    }

    public ExpressionParser(Reader reader) throws IOException {
        this(new Lexer(reader));
    }

    public ExpressionParser(Lexer lexer) {
        super(lexer);
        states = new ArrayList<>();
    }

    @Override
    protected void initialise() {
        State state;
        for (int index = 0; index < stateCount; index++) {
            states.add(new State());
        }

        // first state
        state = states.get(0);
        state.putAction(TerminalTag.COSINE, new ShiftAction(states.get(2)));
        state.putAction(TerminalTag.NUMBER, new ShiftAction(states.get(3)));
        state.putNextState(NonterminalTag.EXPRESSION, states.get(1));

        // second state
        state = states.get(1);
        state.putAction(TerminalTag.PLUS, new ShiftAction(states.get(4)));
        state.putAction(TerminalTag.MINUS, new ShiftAction(states.get(5)));
        state.putAction(TerminalTag.POWER, new ShiftAction(states.get(6)));
        state.putAction(null, new AcceptAction());

        // third state
        state = states.get(2);
        state.putAction(TerminalTag.COSINE, new ShiftAction(states.get(2)));
        state.putAction(TerminalTag.NUMBER, new ShiftAction(states.get(3)));
        state.putNextState(NonterminalTag.EXPRESSION, states.get(8));

        // fourth state
        state = states.get(3);
        state.putAction(TerminalTag.PLUS, new ReduceAction(productions.get(5)));
        state.putAction(TerminalTag.MINUS, new ReduceAction(productions.get(5)));
        state.putAction(TerminalTag.COSINE, new ReduceAction(productions.get(5)));
        state.putAction(TerminalTag.FACTORIAL, new ReduceAction(productions.get(5)));
        state.putAction(null, new ReduceAction(productions.get(5)));

        // fifth state
        state = states.get(4);
        state.putAction(TerminalTag.COSINE, new ShiftAction(states.get(2)));
        state.putAction(TerminalTag.NUMBER, new ShiftAction(states.get(3)));
        state.putNextState(NonterminalTag.EXPRESSION, states.get(9));

        // sixth state
        state = states.get(5);
        state.putAction(TerminalTag.COSINE, new ShiftAction(states.get(2)));
        state.putAction(TerminalTag.NUMBER, new ShiftAction(states.get(3)));
        state.putNextState(NonterminalTag.EXPRESSION, states.get(10));

        // seventh state
        state = states.get(6);
        state.putAction(TerminalTag.COSINE, new ShiftAction(states.get(2)));
        state.putAction(TerminalTag.NUMBER, new ShiftAction(states.get(3)));
        state.putNextState(NonterminalTag.EXPRESSION, states.get(11));

        // eighth state
        state = states.get(7);
        state.putAction(TerminalTag.PLUS, new ReduceAction(productions.get(4)));
        state.putAction(TerminalTag.MINUS, new ReduceAction(productions.get(4)));
        state.putAction(TerminalTag.POWER, new ReduceAction(productions.get(4)));
        state.putAction(TerminalTag.FACTORIAL, new ReduceAction(productions.get(4)));
        state.putAction(null, new ReduceAction(productions.get(4)));

        // ninth state
        state = states.get(8);
        state.putAction(TerminalTag.PLUS, new ReduceAction(productions.get(3)));
        state.putAction(TerminalTag.MINUS, new ReduceAction(productions.get(3)));
        state.putAction(TerminalTag.POWER, new ReduceAction(productions.get(3)));
        state.putAction(TerminalTag.FACTORIAL, new ShiftAction(states.get(7)));
        state.putAction(null, new ReduceAction(productions.get(3)));

        // tenth state
        state = states.get(9);
        state.putAction(TerminalTag.PLUS, new ReduceAction(productions.get(0)));
        state.putAction(TerminalTag.MINUS, new ShiftAction(states.get(5)));
        state.putAction(TerminalTag.POWER, new ShiftAction(states.get(6)));
        state.putAction(TerminalTag.FACTORIAL, new ShiftAction(states.get(7)));
        state.putAction(null, new ReduceAction(productions.get(0)));

        // eleventh state
        state = states.get(10);
        state.putAction(TerminalTag.PLUS, new ReduceAction(productions.get(1)));
        state.putAction(TerminalTag.MINUS, new ReduceAction(productions.get(1)));
        state.putAction(TerminalTag.POWER, new ShiftAction(states.get(6)));
        state.putAction(TerminalTag.FACTORIAL, new ShiftAction(states.get(7)));
        state.putAction(null, new ReduceAction(productions.get(2)));

        // twelfth state
        state = states.get(11);
        state.putAction(TerminalTag.PLUS, new ReduceAction(productions.get(2)));
        state.putAction(TerminalTag.MINUS, new ReduceAction(productions.get(2)));
        state.putAction(TerminalTag.POWER, new ShiftAction(states.get(6)));
        state.putAction(TerminalTag.FACTORIAL, new ShiftAction(states.get(7)));
        state.putAction(null, new ReduceAction(productions.get(2)));

        // remove all existing states from `stateStack`
        stateStack.clear();
        // push the initial state to the stack of states
        stateStack.add(states.get(0));
    }

    public static void parse(String inputString) throws IOException, InvalidTokenException {
        ExpressionParser parser = new ExpressionParser(inputString);
        parser.run();
    }
}

