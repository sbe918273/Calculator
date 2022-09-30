package parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import lexer.IllegalLexemeException;
import lexer.Lexer;
import lexer.token.Token;

import main.UnpositionedException;
import parser.production.Production;
import parser.symbol.Nonterminal;
import parser.symbol.Symbol;
import parser.symbol.Terminal;

/**
 * A class for an SLR parser.
 * @param <TerminalTag> the type of tag for a terminal
 * @param <NonterminalTag> the type of tag for a nonterminal
 */
public abstract class Parser<TerminalTag, NonterminalTag> {
    // the lexer from which the parser receives tokens
    protected final Lexer<TerminalTag> lexer;
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
     * @throws IllegalLexemeException the lexer throws an `IllegalLexemeException`
     */
    public Nonterminal<TerminalTag, NonterminalTag> run() throws
        IOException,
        IllegalLexemeException,
        IllegalTokenException
    {
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
     * @throws IllegalLexemeException the lexer throws an `IllegalLexemeException`
     */
    protected void advanceToken() throws IOException, IllegalLexemeException {
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
         * @throws IllegalLexemeException the lexer encounters an invalid token
         */
        @Override
        public void execute() throws IOException, IllegalLexemeException {
            // push the next state to the stack
            stateStack.push(nextState);
            // construct a terminal from the current token and push it to the symbol stack
            symbolStack.push(new Terminal<>(getToken()));
            // advance the token
            advanceToken();
        }
    }

    /**
     * A class to represent a reduce action that combines symbols on the stack to form a production's nonterminal head.
     */
    protected class ReduceAction extends OptionallyNamed implements Action {

        private final Production<TerminalTag, NonterminalTag> production;

        /**
         * A constructor to initialise this action's corresponding production.
         * The action's name is "[REDUCE `production`]".
         * @param production the production by which to combine symbols on the stack
         */
        public ReduceAction(Production<TerminalTag, NonterminalTag> production) {
            super(String.format("[REDUCE %s]", production.toString()));
            this.production = production;
        }

        /**
         * Executes this action. Combines top symbols on the stack to form the head of this action's production.
         */
        @Override
        public void execute() {
            // collect as many symbols from the top of stack as the `production`'s length
            LinkedList<Symbol<TerminalTag, NonterminalTag>> children = new LinkedList<>();
            for (int index = 0; index < production.getLength(); index++) {
                stateStack.pop();
                children.push(symbolStack.pop());
            }

            // retrieve the next state after encountering `production`'s head and push it onto the stack
            NonterminalTag productionTag = production.getTag();
            State<TerminalTag, NonterminalTag> nextState = stateStack.peek().getNextState(productionTag);
            stateStack.push(nextState);

            // construct a nonterminal from the symbol sequence and push it to the symbol stack
            Nonterminal<TerminalTag, NonterminalTag> parentNonterminal = production.createNonterminal(children);
            symbolStack.push(parentNonterminal);
        }
    }

    /**
     * A class to represent an exception action (an action that throws an `IllegalTokenException`).
     */
    protected abstract class ExceptionAction extends OptionallyNamed implements Action {

        /**
         * Initialises this action's name to be "[EXCEPTION `exceptionName`]".
         * @param exceptionName the name of this action's exception
         */
        public ExceptionAction(String exceptionName) {
            super(String.format("[EXCEPTION %s]", exceptionName));
        }
    }

    protected class ExpectedOperatorExceptionAction extends ExceptionAction {

        // the description of this action's exception
        private final String description;

        /**
         * Initialises this action's description. The action's name is "[EXCEPTION ExceptedOperatorException]".
         * @param description this action's description
         */
        public ExpectedOperatorExceptionAction(String description) {
            super("ExpectedOperatorException");
            this.description = description;
        }

        /**
         * Executes this action by throwing an `ExpectedOperatorException` that includes this action's description and
         * the lexer's current line and character numbers.
         * @throws ExpectedOperatorException this action always throws an `ExpectedOperatorException`
         */
        @Override
        public void execute() throws ExpectedOperatorException {
            throw new ExpectedOperatorException(
                "ExpectedOperatorExceptionAction",
                "execute",
                lexer.getLineNumber(),
                lexer.getCharacterNumber(),
                description
            );
        }
    }

    protected class ExpectedOperandExceptionAction extends ExceptionAction {

        // the description of this action's exception
        private final String description;

        /**
         * Initialises this action's description. The action's name is "[EXCEPTION ExpectedOperandException]".
         * @param description this action's description
         */
        public ExpectedOperandExceptionAction(String description) {
            super("ExpectedOperandException");
            this.description = description;
        }

        /**
         * Executes this action by throwing an `ExpectedOperandException` that includes this action's description and
         * the lexer's current line and character numbers.
         * @throws ExpectedOperandException this action always throws an `ExpectedOperandException`
         */
        @Override
        public void execute() throws ExpectedOperandException {
            throw new ExpectedOperandException(
                    "ExpectedOperandExceptionAction",
                    "execute",
                    lexer.getLineNumber(),
                    lexer.getCharacterNumber(),
                    description
            );
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
