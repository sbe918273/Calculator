package parser;

import lexer.InvalidTokenException;

import java.io.IOException;

/**
 * A class for an action of an SLR parsing table. For example, shift, reduce, accept or error.
 */
abstract public class Action {

    // the action's (optional) name
    private final String name;

    /**
     * A constructor to initialise this action with no name.
     */
    public Action() {
        this(null);
    }

    /**
     * A constructor initialise this action's name
     * @param name a name
     */
    public Action(String name) {
        this.name = name;
    }

    /**
     * Executes this action. For example, by updating the state of an outer `Parser` object and/or reading a token.
     * @throws IOException the lexer's reader throws an IO exception
     * @throws InvalidTokenException the lexer encounters an invalid token
     */
    abstract void execute() throws IOException, InvalidTokenException;

    /**
     * Retrieves this action's string representation.
     * If `name` is null then the representation is that of its super class. Otherwise, it is the action's name.
     * @return this action's string representation
     */
    @Override
    public String toString() {
        // We return the super class's string representation iff the action does not have a name.
        if (name == null) {
            return super.toString();
        }
        // Otherwise, we return the action's name.
        return name;
    }
}
