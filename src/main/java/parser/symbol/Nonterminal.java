package parser.symbol;

import java.util.List;

/**
 * A class for a nonterminal that has a tag and has symbol children.
 * We require `TerminalTag` because this nonterminal's children are either terminals or nonterminals.
 * Additionally, we can store this nonterminal with terminals in an appropriate symbol array.
 * @param <NonterminalTag> the type of tag for this nonterminal
 * @param <TerminalTag> the type of for this nonterminal's constituent terminals
 */
public class Nonterminal<TerminalTag, NonterminalTag> implements Symbol<TerminalTag, NonterminalTag> {

    // this nonterminal's tag
    private final NonterminalTag tag;
    // this nonterminal's children (the symbols appearing in the same order in this nonterminal's production)
    private final List<Symbol<TerminalTag, NonterminalTag>> children;

    /**
     * A constructor to initialise this nonterminal's tag and children.
     * @param tag this nonterminal's tag
     * @param children this nonterminal's children
     */
    public Nonterminal(
            NonterminalTag tag,
            List<Symbol<TerminalTag, NonterminalTag>> children
    ) {
        this.tag = tag;
        this.children = children;
    }

    /**
     * @return this nonterminal's tag
     */
    public NonterminalTag getTag() {
        return tag;
    }

    /**
     * @return this nonterminal's children
     */
    public List<Symbol<TerminalTag, NonterminalTag>> getChildren() {
        return children;
    }

    /**
     * @return this nonterminal
     */
    @Override
    public Nonterminal<TerminalTag, NonterminalTag> getNonterminal() {
        return this;
    }

    /**
     * Retrieves the string representation of this nonterminal.
     * The string is "[`tag`]".
     * @return this nonterminal's string representation
     */
    @Override
    public String toString() {
        return "[" + getTag() + "]";
    }
}
