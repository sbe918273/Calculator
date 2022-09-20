package parser;

/**
 * A class for a production: a nonterminal tag head mapped to a symbol sequence body.
 * @param <T> an enumeration of nonterminal tags
 */
public class Production<NonterminalTag> {

    // this production's  head
    private final NonterminalTag head;
    // the length of (number of symbols in the body of) this production
    private final int length;

    // a constructor to initialise this production's head and length
    public Production(NonterminalTag head, int length) {
        this.head = head;
        this.length = length;
    }

    /**
     * @return this production's head
     */
    public NonterminalTag getHead() {
        return head;
    }

    /**
     * @return this production's length
     */
    public int getLength() {
        return length;
    }
}