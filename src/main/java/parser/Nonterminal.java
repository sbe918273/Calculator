package parser;

import java.util.List;

/**
 * A class for a nonterminal that has a tag and has symbol children.
 * @param <NonterminalTag> the type of tag for this nonterminal
 * @param <TokenTag> the type of for this nonterminal's constituent tokens
 */
public interface Nonterminal<TokenTag, NonterminalTag> extends Symbol<TokenTag, NonterminalTag> {
    /**
     * @return this nonterminal's tag
     */
    NonterminalTag getTag();

    /**
     * @return this nonterminal's children
     */
    List<Symbol<TokenTag, NonterminalTag>> getChildren();
}
