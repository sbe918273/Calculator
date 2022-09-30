package main;

import parser.IllegalTokenException;
import parser.symbol.ExpressionNonterminal;
import parser.ExpressionParser;
import lexer.IllegalLexemeException;

import java.io.IOException;

public class Main {

    /**
     * Prints the value of an arithmetic expression that is an input string using an `ExpressionParser`.
     * The lexer is an `ExpressionLexer`.
     * @param args an array containing exactly one input string
     * @throws IOException the lexer throws an IO exception
     * @throws IllegalLexemeException the lexer throws an `IllegalLexemeException`
     * @throws IllegalTokenException the lexer throws an `IllegalTokenException`
     */
    public static void main(String[] args) throws IOException, IllegalLexemeException, IllegalTokenException {
        // ensure that only one input string is present
        if (args.length != 1) {
            throw new IllegalArgumentException("[Main:main] Expected exactly one input string.");
        }

        // retrieve the input string
        String inputString = args[0];
        // parse the input string
        ExpressionNonterminal rootNonterminal = ExpressionParser.parse(inputString);
        // output the result
        System.out.printf("%s = %f", inputString, rootNonterminal.getValue());
    }
}
