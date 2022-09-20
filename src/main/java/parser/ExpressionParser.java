package parser;

import lexer.InvalidTokenException;
import lexer.Lexer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ExpressionParser extends Parser {

    private final List<State> states;
    private static final List<Production> productions;
    private static final int stateCount = 12;

    static {
        productions = List.of(
            // E -> E + E
            new Production("EXPRESSION", 3),
            // E -> E - E
            new Production("EXPRESSION", 3),
            // E -> E ^ E
            new Production("EXPRESSION", 3),
            // E -> cos E
            new Production("EXPRESSION", 2),
            // E -> E!
            new Production("EXPRESSION", 2),
            // E -> number
            new Production("EXPRESSION", 1)
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
        state.putAction("COSINE", new ShiftAction(states.get(2)));
        state.putAction("NUMBER", new ShiftAction(states.get(3)));
        state.putNextState("EXPRESSION", states.get(1));

        // second state
        state = states.get(1);
        state.putAction("PLUS", new ShiftAction(states.get(4)));
        state.putAction("MINUS", new ShiftAction(states.get(5)));
        state.putAction("POWER", new ShiftAction(states.get(6)));
        state.putAction(null, new AcceptAction());

        // third state
        state = states.get(2);
        state.putAction("COSINE", new ShiftAction(states.get(2)));
        state.putAction("NUMBER", new ShiftAction(states.get(3)));
        state.putNextState("EXPRESSION", states.get(8));

        // fourth state
        state = states.get(3);
        state.putAction("PLUS", new ReduceAction(productions.get(5)));
        state.putAction("MINUS", new ReduceAction(productions.get(5)));
        state.putAction("COSINE", new ReduceAction(productions.get(5)));
        state.putAction("FACTORIAL", new ReduceAction(productions.get(5)));
        state.putAction(null, new ReduceAction(productions.get(5)));

        // fifth state
        state = states.get(4);
        state.putAction("COSINE", new ShiftAction(states.get(2)));
        state.putAction("NUMBER", new ShiftAction(states.get(3)));
        state.putNextState("EXPRESSION", states.get(9));

        // sixth state
        state = states.get(5);
        state.putAction("COSINE", new ShiftAction(states.get(2)));
        state.putAction("NUMBER", new ShiftAction(states.get(3)));
        state.putNextState("EXPRESSION", states.get(10));

        // seventh state
        state = states.get(6);
        state.putAction("COSINE", new ShiftAction(states.get(2)));
        state.putAction("NUMBER", new ShiftAction(states.get(3)));
        state.putNextState("EXPRESSION", states.get(11));

        // eighth state
        state = states.get(7);
        state.putAction("PLUS", new ReduceAction(productions.get(4)));
        state.putAction("MINUS", new ReduceAction(productions.get(4)));
        state.putAction("POWER", new ReduceAction(productions.get(4)));
        state.putAction("FACTORIAL", new ReduceAction(productions.get(4)));
        state.putAction(null, new ReduceAction(productions.get(4)));

        // ninth state
        state = states.get(8);
        state.putAction("PLUS", new ReduceAction(productions.get(3)));
        state.putAction("MINUS", new ReduceAction(productions.get(3)));
        state.putAction("POWER", new ReduceAction(productions.get(3)));
        state.putAction("FACTORIAL", new ShiftAction(states.get(7)));
        state.putAction(null, new ReduceAction(productions.get(3)));

        // tenth state
        state = states.get(9);
        state.putAction("PLUS", new ReduceAction(productions.get(0)));
        state.putAction("MINUS", new ShiftAction(states.get(5)));
        state.putAction("POWER", new ShiftAction(states.get(6)));
        state.putAction("FACTORIAL", new ShiftAction(states.get(7)));
        state.putAction(null, new ReduceAction(productions.get(0)));

        // eleventh state
        state = states.get(10);
        state.putAction("PLUS", new ReduceAction(productions.get(1)));
        state.putAction("MINUS", new ReduceAction(productions.get(1)));
        state.putAction("POWER", new ShiftAction(states.get(6)));
        state.putAction("FACTORIAL", new ShiftAction(states.get(7)));
        state.putAction(null, new ReduceAction(productions.get(2)));

        // twelfth state
        state = states.get(11);
        state.putAction("PLUS", new ReduceAction(productions.get(2)));
        state.putAction("MINUS", new ReduceAction(productions.get(2)));
        state.putAction("POWER", new ShiftAction(states.get(6)));
        state.putAction("FACTORIAL", new ShiftAction(states.get(7)));
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

