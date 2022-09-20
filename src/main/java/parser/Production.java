package parser;

/**
 * A class for a production: a nonterminal tag head mapped to a symbol sequence body.
 * @param <T> an enumeration of nonterminal tags
 */
public class Production {

    // this production's tag
    private String tag;
    // the length of (number of symbols in the body of) this production
    private final int length;

    // a constructor to initialise this production's head and length
    public Production(String tag, int length) {
        this.tag = tag;
        this.length = length;
    }

    /**
     * @return this production's head
     */
    public String getTag() {
        return tag;
    }

    /**
     * @return this production's length
     */
    public int getLength() {
        return length;
    }
}