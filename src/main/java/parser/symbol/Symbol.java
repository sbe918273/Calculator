package parser.symbol;

import lexer.token.Token;

import java.util.Iterator;
import java.util.List;

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

    /**
     * Determines whether this symbol is fuzzily equal to another. Defaults to the existing `equals` method.
     * @param other a symbol
     * @return whether this symbol is fuzzily equal to the symbol
     */
    default boolean fuzzyEquals(Object other) {
        return equals(other);
    }

    /**
     * Determines whether two symbol arrays are element-wise structurally equal.
     * @param observedSymbols an array of observed symbols
     * @param expectedSymbols an array of expected symbols
     * @param <TerminalTag> the type of tag for a terminal
     * @param <NonterminalTag> the type of tag for a nonterminal
     * @return whether the arrays are equal
     */
    static <TerminalTag, NonterminalTag>boolean fuzzyListEquals(
            List<Symbol<TerminalTag, NonterminalTag>> observedSymbols,
            List<Symbol<TerminalTag, NonterminalTag>> expectedSymbols
    ) {
        // return `true` iff both lists are `null`
        if (observedSymbols == null) {
            return expectedSymbols == null;
        } else {
            // return `false` if the lists' length are not equal
            if (observedSymbols.size() != expectedSymbols.size()) { return false; }
            // return `false` if the symbols in `expectedSymbols` do not equal the symbols in `observedSymbols`
            // We iterate simultaneously over the symbol lists using their respective iterators.
            Iterator<Symbol<TerminalTag, NonterminalTag>> observedIterator = observedSymbols.iterator();
            Iterator<Symbol<TerminalTag, NonterminalTag>> expectedIterator = expectedSymbols.iterator();
            while (observedIterator.hasNext()) {
                Symbol<TerminalTag, NonterminalTag> observedSymbol = observedIterator.next();
                Symbol<TerminalTag, NonterminalTag> expectedSymbol = expectedIterator.next();
                // `null` symbols are equal
                if (observedSymbol == null) {
                    if (expectedSymbol != null) { return false; }
                } else {
                    if (!observedSymbol.fuzzyEquals(expectedSymbol)) { return false; }
                }
            }
            // return `true` iff none of the above checks failed
            return true;
        }
    }
}
