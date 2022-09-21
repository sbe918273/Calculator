package parser;

import lexer.ExpressionTokenTag;
import lexer.InvalidTokenException;
import lexer.Lexer;
import lexer.Token;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * A class for an SLR parser.
 * @param <TerminalTag> the type of tag for a terminal
 * @param <NonterminalTag> the type of tag for a nonterminal
 */
public abstract class Parser<TerminalTag, NonterminalTag> {
    // the lexer from which the parser receives tokens
    private final Lexer<TerminalTag> lexer;
    // a stack of states representing the state of the parser's automaton
    protected final Stack<State<TerminalTag, NonterminalTag>> stateStack;
    // a stack of symbols corresponding element-wise to the states in `stateStack`
    protected final Stack<Symbol<TerminalTag, NonterminalTag>> symbolStack;
    // the most recently read token
    private Token<TerminalTag> token;
    // whether the automaton driver has accepted the input
    protected boolean accepted = false;

    /**
     * A constructor to initialise the lexer to that provided and `stateStack` and `symbolStack` to be empty.
     */
    public Parser(Lexer<TerminalTag> lexer) {
        this.lexer = lexer;
        stateStack = new Stack<>();
        symbolStack = new Stack<>();
    }

    /**
     * Initialises the parser's state. For example, create all the `State` objects and respectively push to the state
     * and symbol stacks a state and symbol that initialises the automaton. The SLR algorithm drives the automaton.
     */
    protected abstract void initialise();

    /**
     * Generates a parse tree from the lexer's token stream.
     * Implements the SLR algorithm by driving `initialise`'s resulting automaton.
     * @return the parse tree (as a `Nonterminal` object)
     * @throws IOException the lexer throws an IO exception
     * @throws InvalidTokenException the lexer throws an `InvalidTokenException`
     */
    public Nonterminal<TerminalTag, NonterminalTag> run() throws IOException, InvalidTokenException {
        // initialise the parser's state
        initialise();

        // retrieve the first token
        token = lexer.scan();
        // The driver continues driving the automaton iff it does not accept the input.
        while (!accepted) {
            // `tag` is the current token's tag. The tag is `null` is the token is `null`.
            TerminalTag tag = token == null ? null : token.getTag();
            // retrieve the action for the tag at the current state
            Action action = stateStack.peek().getAction(tag);
            // execute the action
            action.execute();
        }
        // assert that the resulting symbol is a nonterminal
        if (symbolStack.peek() instanceof Nonterminal<TerminalTag, NonterminalTag> rootNonterminal) {
            return rootNonterminal;
        }
        throw new IllegalStateException("[Parser:run] Root symbol cannot be a terminal.");
    }

    /**
     * Advances the current token.
     * @throws IOException the lexer throws an IO exception
     * @throws InvalidTokenException the lexer throws an `InvalidTokenException`
     */
    protected void advanceToken() throws IOException, InvalidTokenException {
        token = lexer.scan();
    }

    /**
     * @return the current token
     */
    protected Token<TerminalTag> getToken() {
        return token;
    }

    /**
     * A class to represent a shift action that advances the current token and pushes a state to the stack.
     */
    protected class ShiftAction extends OptionallyNamed implements Action {

        // the state to push to the stack
        private final State<TerminalTag, NonterminalTag> nextState;

        /**
         * A constructor to initialise this action's corresponding state. The action's name is "[SHIFT `nextState`]".
         * @param nextState the state to push to the stack
         */
        public ShiftAction(State<TerminalTag, NonterminalTag> nextState) {
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
            symbolStack.push(new Terminal<>(getToken()));
            // advance the token
            advanceToken();
        };
    }

    /**
     * A class to represent a reduce action.
     */
    protected class ReduceAction extends OptionallyNamed implements Action {

        private final Production<TerminalTag, NonterminalTag> production;

        public ReduceAction(Production<TerminalTag, NonterminalTag> production) {
            super(String.format("[REDUCE %s]", production.toString()));
            this.production = production;
        }

        @Override
        public void execute() {
            LinkedList<Symbol<TerminalTag, NonterminalTag>> children = new LinkedList<>();

            for (int index = 0; index < production.getLength(); index++) {
                stateStack.pop();
                children.push(symbolStack.pop());
            }

            NonterminalTag productionTag = production.getTag();
            State<TerminalTag, NonterminalTag> nextState = stateStack.peek().getNextState(productionTag);
            stateStack.push(nextState);
            // construct a nonterminal from the production's tag and the popped symbols and push it to the symbol stack
            symbolStack.push(new Nonterminal<>(productionTag, children));
        }
    }

    protected class AcceptAction extends OptionallyNamed implements Action {

        public AcceptAction() {
            super("[ACCEPT]");
        }

        /**
         * Executes the action by setting the driver to accept the input.
         */
        @Override
        public void execute() {
            accepted = true;
        }
    }
}
