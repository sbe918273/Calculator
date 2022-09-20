package parser;

import java.util.List;

/**
 * A class for a nonterminal that has a string tag and has symbol children.
 */
public class Nonterminal implements Symbol {

    // this nonterminal's tag
    private final String tag;
    // this nonterminal's children (the symbols appearing in the same order in this nonterminal's production)
    private final List<Symbol> children;

    /**
     * A constructor to initialise this nonterminal's tag and children
     * @param tag this nonterminal's tag
     * @param children this nonterminal's children
     */
    public Nonterminal(String tag, List<Symbol> children) {
        this.tag = tag;
        this.children = children;
    }

    /**
     * @return this nonterminal's tag
     */
    @Override
    public String getTag() {
        return tag;
    }

    /**
     * @return this nonterminal's children
     */
    public List<Symbol> getChildren() {
        return children;
    }
}
