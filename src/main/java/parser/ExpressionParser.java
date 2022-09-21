package parser;

import lexer.ExpressionTokenTag;
import lexer.InvalidTokenException;
import lexer.ExpressionLexer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExpressionParser extends Parser<ExpressionTokenTag> {

    private static final List<Production> productions;
    private static final int stateCount = 12;

    static {
        productions = List.of(
            // E -> E + E
            new Production("0", "EXPRESSION", 3),
            // E -> E - E
            new Production("1", "EXPRESSION", 3),
            // E -> E ^ E
            new Production("2", "EXPRESSION", 3),
            // E -> cos E
            new Production("3", "EXPRESSION", 2),
            // E -> E!
            new Production("4", "EXPRESSION", 2),
            // E -> number
            new Production("5", "EXPRESSION", 1)
        );
    }

    public ExpressionParser(String inputString) throws IOException {
        this(new ExpressionLexer(inputString));
    }

    public ExpressionParser(ExpressionLexer expressionLexer) {
        super(expressionLexer);
    }

    /**
     * A class to represent a shift action that advances the current token and pushes a state to the stack.
     */
    protected class ShiftAction extends OptionallyNamed implements Action {

        // the state to push to the stack
        private final State<ExpressionTokenTag> nextState;

        /**
         * A constructor to initialise this action's corresponding state. The action's name is "[SHIFT `nextState`]".
         * @param nextState the state to push to the stack
         */
        public ShiftAction(State<ExpressionTokenTag> nextState) {
            super(String.format("[SHIFT %s]", nextState.toString()));
            this.nextState = nextState;
        }

        /**
         * Executes this action. Pushes the next state to the stack and advances the current token.
         * @throws IOException the lexer's reader throws an IO exception
         * @throws InvalidTokenException the lexer encounters an invalid token
         */
        @Override
        public void execute() throws IOException, InvalidTokenException {
            // push the next state to the stack
            stateStack.push(nextState);
            // construct a terminal from the current token and push it to the symbol stack
            symbolStack.push(new getToken());
            // advance the token
            advanceToken();
        };
    }

    /**
     * A class to represent a reduce action.
     */
    protected class ReduceAction extends OptionallyNamed implements Action {

        private final Production production;

        public ReduceAction(Production production) {
            super(String.format("[REDUCE %s]", production.toString()));
            this.production = production;
        }

        @Override
        public void execute() {
            LinkedList<Symbol<ExpressionTokenTag>> children = new LinkedList<>();

            for (int index = 0; index < production.getLength(); index++) {
                stateStack.pop();
                children.push(symbolStack.pop());
            }

            String productionTag = production.getTag();
            State<ExpressionTokenTag> nextState = stateStack.peek().getNextState(productionTag);
            stateStack.push(nextState);
            // construct a nonterminal from the production's tag and the popped symbols and push it to the symbol stack
            symbolStack.push(new Nonterminal(productionTag, children));
        }
    }

    @Override
    protected void initialise() {
        State<ExpressionTokenTag> state;
        final List<State<ExpressionTokenTag>> states = new LinkedList<>();
        for (int index = 0; index < stateCount; index++) {
            states.add(new State(Integer.toString(index)));
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
        state.putAction("FACTORIAL", new ShiftAction(states.get(7)));
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
        symbolStack.add(null);
    }

    public static Nonterminal parse(String inputString) throws IOException, InvalidTokenException {
        ExpressionParser expressionParser = new ExpressionParser(inputString);
        return expressionParser.run();
    }
}

