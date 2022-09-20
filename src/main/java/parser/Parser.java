package parser;

import lexer.InvalidTokenException;
import lexer.Lexer;
import lexer.Token;

import java.io.IOException;
import java.util.Stack;

/**
 * A class for an SLR parser.
 */
public abstract class Parser {
    // the lexer from which the parser receives tokens
    private final Lexer lexer;
    // a stack of states representing the parser's complete state
    protected final Stack<State> stateStack;
    // the most recently read token
    private Token token;

    // a constructor to initialise the lexer to that provided and `stateStack` to be empty
    public Parser(Lexer lexer) {
        this.lexer = lexer;
        stateStack = new Stack<>();
    }

    /**
     * Initialises the parser's state. This parser calls it before running.
     */
    protected abstract void initialise();

    /**
     * Runs the parser. Produces the resulting
     * @throws IOException
     * @throws InvalidTokenException
     */
    public void run() throws IOException, InvalidTokenException {
        initialise();
        token = lexer.scan();
        Action action = null;
        while (!(action instanceof AcceptAction)) {
            String tag = token == null ? null : token.getTag();
            State currentState = stateStack.peek();
            action = currentState.getAction(tag);
            action.execute();
        }
    }

    /**
     * A class to represent a shift action that advances the current token and pushes a state to the stack.
     */
    protected class ShiftAction extends Action {

        // the state to push to the stack
        private final State nextState;

        /**
         * A constructor to initialise this action's corresponding state without giving a name.
         * @param nextState the state to push to the stack
         */
        public ShiftAction(State nextState) {
            this(null, nextState);
        }

        /**
         * A constructor to initialise this action's name and corresponding state.
         * @param name this action's name
         * @param nextState the state to push to the stack
         */
        public ShiftAction(String name, State nextState) {
            super(name);
            this.nextState = nextState;
        }

        /**
         * Executes this action. Pushes the next state to the stack and advances the current token.
         * @throws IOException the lexer's reader throws an IO exception
         * @throws InvalidTokenException the lexer encounters an invalid token
         */
        @Override
        public void execute() throws IOException, InvalidTokenException {
            // initialise `nextState`'s tag (if not already done) to be `token`
            nextState.setTag(token.getTag());
            // push the next state to the stack
            stateStack.push(nextState);
            // advance the token
            token = lexer.scan();
        };
    }

    /**
     * A class to represent a reduce action.
     */
    protected class ReduceAction extends Action {

        private final Production production;

        public ReduceAction(Production production) {
            this(null, production);
        }

        public ReduceAction(String name, Production production) {
            super(name);
            this.production = production;
        }

        @Override
        public void execute() {
            for (int index = 0; index < production.getLength(); index++) {
                stateStack.pop();
            }
            String productionTag = production.getTag();
            State currentState = stateStack.peek();
            State nextState = stateStack.peek().getNextState(productionTag);
            nextState.setTag(productionTag);
            stateStack.push(nextState);
        }
    }

    protected static class AcceptAction extends Action {

        public AcceptAction(String name) {
            super(name);
        }

        public AcceptAction() {
            this("[ACCEPT]");
        }

        @Override
        public void execute() {}
    }
}
