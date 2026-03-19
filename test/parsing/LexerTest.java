package test.parsing;

import java.util.*;
import src.parsing.lexer.Lexer;
import src.parsing.lexer.Token;

public class LexerTest {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();

        String sourceCode = "var x = 5;";

        List<Token> tokens;
        try {
            tokens = lexer.tokenize(sourceCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // compare types
        
        List<String> expectedTypes = Arrays.asList(
            "Var", "Identifier", "Equals", "Number", "Semicolon", "EOF"
        );

        List<String> actualTypes = new ArrayList<>();
        for (Token t : tokens) {
            actualTypes.add(t.getType());
        }

        System.out.println("Expected: " + expectedTypes);
        System.out.println("Actual:   " + actualTypes);

        if (expectedTypes.equals(actualTypes)) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }

        // compare values

        List<String> expectedValues = Arrays.asList(
            "var", "x", "=", "5", ";", "EOF"
        );

        List<String> actualValues = new ArrayList<>();
        for (Token t : tokens) {
            actualValues.add(t.getValue());
        }

        System.out.println("Expected: " + expectedValues);
        System.out.println("Actual:   " + actualValues);

        if (expectedValues.equals(actualValues)) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
    }
}