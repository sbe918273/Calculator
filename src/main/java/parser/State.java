package parser;

import java.util.HashMap;

/**
 * A class to represent a state in an SLR parser's automaton.
 * A state can present the action to execute upon encountering (after a shift) a token tag.
 * A state can present the next state upon encountering (after a reduction) a nonterminal tag.
 * @param <TokenTag> the type of tag for a token
 * @param <NonterminalTag> the type of tag for a nonterminal
 */
public class State<TokenTag, NonterminalTag> extends OptionallyNamed {

    // a mapping from token tags to actions
    private final HashMap<TokenTag, Action> actions;
    // a mapping from nonterminal tags to next states
    private final HashMap<NonterminalTag, State<TokenTag, NonterminalTag>> nextStates;

    /**
     * Initialises both mappings to be empty hashmaps. The state has no name.
     */
    public State() {
        this(null);
    }

    /**
     * Initialises this state's name to be that provided and both mappings to be empty hashmaps.
     * @param name this state's name
     */
    public State(String name) {
        super(name);
        actions = new HashMap<>();
        nextStates = new HashMap<>();
    }

    /**
     * Retrieves the action for a token tag.
     * @param tokenTag a token tag
     * @return an action
     */
    public Action getAction(TokenTag tokenTag) {
        return actions.get(tokenTag);
    }

    /**
     * Retrieves the next state for a nonterminal tag.
     * @param nonterminalTag a nonterminal tag
     * @return the next state
     */
    public State getNextState(NonterminalTag nonterminalTag) {
        return nextStates.get(nonterminalTag);
    }

    /**
     * Registers the action for a token tag.
     * @param tokenTag a token tag
     * @param action the action for the token tag
     */
    public void putAction(TokenTag tokenTag, Action action) {
        actions.put(tokenTag, action);
    }

    /**
     * Registers the next state for a nonterminal tag.
     * @param nonterminalTag a nonterminal tag
     * @param state the next state for the nonterminal tag
     */
    public void putNextState(NonterminalTag nonterminalTag, State<TokenTag, NonterminalTag> state) {
        nextStates.put(nonterminalTag, state);
    }
}
