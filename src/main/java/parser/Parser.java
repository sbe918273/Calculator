package parser;

import lexer.InvalidTokenException;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenTag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;


/**
 * A class for an SLR parser.
 * @param <T> the tag type for symbols
 */
public abstract class Parser<T extends SymbolTag> {
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
            TokenTag tag = token == null ? null : token.getTag();
            State currentState = stateStack.peek();
            action = currentState.getAction(tag);
            action.execute();
        }
    }

    /**
     * A class to represent state in this parser's automaton.
     * A state can present the action to execute upon encountering a token tag.
     * A state can present the next state upon encountering (after a reduction) a nonterminal.
     */
    protected class State {

        // a mapping from tags to actions
        private final HashMap<SymbolTag, Action> actions;
        // a mapping from nonterminals to next states
        private final HashMap<NonterminalTag, State> nextStates;

        /**
         * Initialises both mappings to be empty hashmaps.
         */
        public State() {
            actions = new HashMap<>();
            nextStates = new HashMap<>();
        }

        /**
         * Retrieves the action for a tag.
         * @param tag a tag
         * @return an action
         */
        public Action getAction(TerminalTag tag) {
            return actions.get(tag);
        }

        /**
         * Retrieves the next state for a nonterminal
         * @param nonterminal a nonterminal
         * @return the next state
         */
        public State getNextState(NonterminalTag nonterminal) {
            return nextStates.get(nonterminal);
        }

        /**
         * Registers the action for a tag.
         * @param tag a tag
         * @param action the action for the tag
         */
        public void putAction(TerminalTag tag, Action action) {
            actions.put(tag, action);
        }

        /**
         * Registers the next state for a nonterminal
         * @param nonterminal a nonterminal
         * @param state the next state for the nonterminal
         */
        public void putNextState(NonterminalTag nonterminal, State state) {
            nextStates.put(nonterminal, state);
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
        public void execute() throws IOException, InvalidTokenException  {
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

        private final Production<NonterminalTag> production;

        public ReduceAction(Production<NonterminalTag> production) {
            this(null, production);
        }

        public ReduceAction(String name, Production<NonterminalTag> production) {
            super(name);
            this.production = production;
        }

        @Override
        public void execute() {
            for (int index = 0; index < production.getLength(); index++) {
                stateStack.pop();
            }
            State currentState = stateStack.peek();
            State nextState = currentState.getNextState(production.getHead());
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
