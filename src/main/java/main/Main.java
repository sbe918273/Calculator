package main;

import parser.ExpressionParser;
import lexer.InvalidTokenException;
import parser.Nonterminal;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidTokenException {
        Nonterminal rootNonterminal = ExpressionParser.parse("3+cos4!");
        System.out.println(rootNonterminal);
    }
}
