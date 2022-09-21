package parser;

import lexer.InvalidTokenException;
import lexer.Lexer;
import lexer.Token;

import java.io.IOException;
import java.util.Stack;

/**
 * A class for an SLR parser.
 * @param <TokenTag> the type of tag for a token
 * @param <NonterminalTag> the type of tag for a nonterminal
 */
public abstract class Parser<TokenTag, NonterminalTag> {
    // the lexer from which the parser receives tokens
    private final Lexer<TokenTag> lexer;
    // a stack of states representing the state of the parser's automaton
    protected final Stack<State<TokenTag, NonterminalTag>> stateStack;
    // a stack of symbols corresponding element-wise to the states in `stateStack`
    protected final Stack<Symbol<TokenTag, NonterminalTag>> symbolStack;
    // the most recently read token
    private Token<TokenTag> token;
    // whether the automaton driver has accepted the input
    protected boolean accepted = false;

    /**
     * A constructor to initialise the lexer to that provided and `stateStack` and `symbolStack` to be empty.
     */
    public Parser(Lexer<TokenTag> lexer) {
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
    public Nonterminal<TokenTag, NonterminalTag> run() throws IOException, InvalidTokenException {
        // initialise the parser's state
        initialise();

        // retrieve the first token
        token = lexer.scan();
        // The driver continues driving the automaton iff it does not accept the input.
        while (!accepted) {
            // `tag` is the current token's tag. The tag is `null` is the token is `null`.
            TokenTag tag = token == null ? null : token.getTag();
            // retrieve the action for the tag at the current state
            Action action = stateStack.peek().getAction(tag);
            // execute the action
            action.execute();
        }
        // assert that the resulting symbol is a nonterminal
        if (symbolStack.peek() instanceof Nonterminal<TokenTag, NonterminalTag> rootNonterminal) {
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
    protected Token<TokenTag> getToken() {
        return token;
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
