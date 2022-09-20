package parser;

public enum ExpressionSymbolTag implements SymbolTag {

    PLUS(true),
    MINUS(true),
    POWER(true),
    COSINE(true),
    FACTORIAL(true),
    NUMBER(true),
    EXPRESSION(false);

    private final boolean terminal;

    ExpressionSymbolTag(boolean terminal) {
        this.terminal = terminal;
    }

    @Override
    public boolean isTerminal() {
        return terminal;
    }
}
