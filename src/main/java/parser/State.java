package parser;

import java.util.HashMap;

/**
 * A class to represent a state in an SLR parser's automaton.
 * A state can present the action to execute upon encountering (after a shift) a terminal tag.
 * A state can present the next state upon encountering (after a reduction) a nonterminal.
 */
public class State {

    // a mapping from terminal tags to actions
    private final HashMap<String, Action> actions;
    // a mapping from nonterminal tags to next states
    private final HashMap<String, State> nextStates;
    // `tag` is the tag of the necessarily unique symbol that causes a next state of this state.
    // `tag` is `null` until initialised.
    private String tag = null;

    /**
     * Initialises both mappings to be empty hashmaps.
     */
    public State() {
        actions = new HashMap<>();
        nextStates = new HashMap<>();
    }

    /**
     * Retrieves the action for a terminal tag.
     * @param terminalTag a terminal tag
     * @return an action
     */
    public Action getAction(String terminalTag) {
        return actions.get(terminalTag);
    }

    /**
     * Retrieves the next state for a nonterminal tag.
     * @param nonterminalTag a nonterminal tag
     * @return the next state
     */
    public State getNextState(String nonterminalTag) {
        return nextStates.get(nonterminalTag);
    }

    /**
     * Registers the action for a terminal tag.
     * @param terminalTag a terminal tag
     * @param action the action for the terminal tag
     */
    public void putAction(String terminalTag, Action action) {
        actions.put(terminalTag, action);
    }

    /**
     * Registers the next state for a nonterminal tag.
     * @param nonterminalTag a nonterminal tag
     * @param state the next state for the nonterminal tag
     */
    public void putNextState(String nonterminalTag, State state) {
        nextStates.put(nonterminalTag, state);
    }

    /**
     * @return this state's tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Updates this state's tag.
     * @param tag a tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
