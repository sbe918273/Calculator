package parser;

/**
 * An interface for a grammar symbol that has a string tag and possibly other attributes.
 */
public interface Symbol {
    /**
     * @return this symbol's tag
     */
    String getTag();
}
