package parser;

/**
 * A class for a production: a nonterminal head mapped to a symbol sequence body.
 * @param <NonterminalTag> the type of tag for the nonterminal head
 */
public class Production<NonterminalTag> extends OptionallyNamed {

    // this production's tag
    private final NonterminalTag tag;
    // the length of (number of symbols in the body of) this production
    private final int length;

    /**
     * A constructor to initialise this production's tag and length. The production has no name.
     * @param tag the production's tag
     * @param length the production's length
     */
    public Production(NonterminalTag tag, int length) {
        this(null, tag, length);
    }

    /**
     * A constructor to initialise this production's name, tag and length. The production has no name.
     * @param name the production's name
     * @param tag the production's tag
     * @param length the production's length
     */
    public Production(String name, NonterminalTag tag, int length) {
        super(name);
        this.tag = tag;
        this.length = length;
    }

    /**
     * @return this production's tag
     */
    public NonterminalTag getTag() {
        return tag;
    }

    /**
     * @return this production's length
     */
    public int getLength() {
        return length;
    }
}