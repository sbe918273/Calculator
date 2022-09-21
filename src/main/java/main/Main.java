package main;

import parser.ExpressionNonterminal;
import parser.ExpressionParser;
import lexer.InvalidTokenException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidTokenException {
        ExpressionNonterminal rootNonterminal = ExpressionParser.parse("3+cos4!");
        System.out.println(rootNonterminal);
    }
}
