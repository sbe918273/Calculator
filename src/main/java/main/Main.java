package main;

import parser.ExpressionParser;
import lexer.InvalidTokenException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidTokenException {
        ExpressionParser.parse("3+3");
    }
}
