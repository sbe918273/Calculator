package main;

import parser.IllegalTokenException;
import parser.symbol.ExpressionNonterminal;
import parser.ExpressionParser;
import lexer.IllegalLexemeException;

import java.io.IOException;

public class Main {

    // TODO: comments for `Parser`'s actions and `ExpressionParser`'s automaton

    public static void main(String[] args) throws IOException, IllegalLexemeException, IllegalTokenException {
        String inputString = args[0];
        ExpressionNonterminal rootNonterminal = ExpressionParser.parse(inputString);
        System.out.printf("%s = %.3f", inputString, rootNonterminal.getValue());
    }
}
