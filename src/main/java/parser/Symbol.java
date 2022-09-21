package parser;

/**
 * A class for a symbol (either a terminal or nonterminal).
 * @param <TerminalTag> the type of tag for a terminal
 * @param <NonterminalTag> the type of tag for a nonterminal
 */
public interface Symbol<TerminalTag, NonterminalTag> {
    /**
     * @return this symbol if it is a terminal, otherwise (the default) `null`
     */
    default Terminal<TerminalTag, NonterminalTag> getTerminal() { return null; }

    /**
     * @return a nonterminal if it is a nonterminal, otherwise (the default) `null`
     */
    default Nonterminal<TerminalTag, NonterminalTag> getNonterminal() { return null; }
}
