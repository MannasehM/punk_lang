package test.parsing;

import java.util.*;
import src.parsing.lexer.Lexer;
import src.parsing.lexer.Token;

public class LexerTest {

    private static class UnitTest {
        private Lexer lexer;
        private String sourceCode;

        private List<Token> tokens;

        private List<String> expectedTypes;
        private List<String> actualTypes;

        private List<String> expectedValues;
        private List<String> actualValues;

        private String tokenizeErrMsg;

        public UnitTest(Lexer lexer, String sourceCode) {
            this.lexer = lexer;
            this.sourceCode = sourceCode;

            try {
                tokens = lexer.tokenize(sourceCode);
            } catch (Exception e) {
                tokenizeErrMsg = e.getMessage();
                System.out.println("Error Message: " + tokenizeErrMsg);
                System.out.println("Source Code: " + sourceCode);
                tokens = new ArrayList<Token>();
            }

            actualTypes = new ArrayList<String>();
            actualValues = new ArrayList<String>();
        }

        public void setExpectedTypes(List<String> expectedTypes) {
            this.expectedTypes = expectedTypes;
        }

        public void setExpectedValues(List<String> expectedValues) {
            this.expectedValues = expectedValues;
        }

        private void setActualTypesAndValues() {
            for (Token t : tokens) {
                this.actualTypes.add(t.getType());
                this.actualValues.add(t.getValue());
            }
        }

        public void runTest() throws Exception {
            if (tokenizeErrMsg != null) {
                throw new Exception(tokenizeErrMsg);
            }

            if (expectedTypes == null || expectedValues == null) {
                throw new Exception("Did not use the two setter methods for UnitTest object.");
            }

            setActualTypesAndValues();

            if (expectedTypes.equals(actualTypes) 
                && expectedValues.equals(actualValues)) {
                System.out.println("PASS");
            } else {
                System.out.println("FAIL");
            }
        }
    }

    // public static void main(String[] args) {
    //     UnitTest test1 = new UnitTest(new Lexer(), "var x = 5;");
    //     test1.setExpectedTypes(Arrays.asList(
    //         "Var", "Identifier", "Equals", "Number", "Semicolon", "EOF"
    //     ));
    //     test1.setExpectedValues(Arrays.asList(
    //         "var", "x", "=", "5", ";", "EOF"
    //     ));
    //     try {
    //         test1.runTest();
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }
    // }

    public static void main(String[] args) {

        UnitTest[] tests = new UnitTest[] {

            // var x = 5;
            new UnitTest(new Lexer(), "var x = 5;"),

            // CONST Y = 10;
            new UnitTest(new Lexer(), "CONST Y = 10;"),

            // var z;
            new UnitTest(new Lexer(), "var z;"),

            // Y = 11;
            new UnitTest(new Lexer(), "Y = 11;"),

            // print(x);
            new UnitTest(new Lexer(), "print(x);"),

            // func add(a, b) { a+b; }
            new UnitTest(new Lexer(), "func add(a, b) { a+b; }"),

            // if (1 > 2) { print(true); }
            new UnitTest(new Lexer(), "if (1 > 2) { print(true); }"),

            // elseif (1 == 2) { print(true); }
            new UnitTest(new Lexer(), "elseif (1 == 2) { print(true); }"),

            // else { print(false); }
            new UnitTest(new Lexer(), "else { print(false); }"),

            // print("Hello World");
            new UnitTest(new Lexer(), "print(\"Hello World\");")
        };

        // Expected values for each test
        List<List<String>> expectedTypesList = Arrays.asList(
            Arrays.asList("Var","Identifier","Equals","Number","Semicolon","EOF"),
            Arrays.asList("Const","Identifier","Equals","Number","Semicolon","EOF"),
            Arrays.asList("Var","Identifier","Semicolon","EOF"),
            Arrays.asList("Identifier","Equals","Number","Semicolon","EOF"),
            Arrays.asList("Identifier","OpenParen","Identifier","CloseParen","Semicolon","EOF"),
            Arrays.asList("Function","Identifier","OpenParen","Identifier","Comma","Identifier","CloseParen","OpenCurly","Identifier","BinaryOperator","Identifier","Semicolon","CloseCurly","EOF"),
            Arrays.asList("IfStatement","OpenParen","Number","ComparisonOperator","Number","CloseParen","OpenCurly","Identifier","OpenParen","Identifier","CloseParen","Semicolon","CloseCurly","EOF"),
            Arrays.asList("ElseIfStatement","OpenParen","Number","ComparisonOperator","Number","CloseParen","OpenCurly","Identifier","OpenParen","Identifier","CloseParen","Semicolon","CloseCurly","EOF"),
            Arrays.asList("ElseStatement","OpenCurly","Identifier","OpenParen","Identifier","CloseParen","Semicolon","CloseCurly","EOF"),
            Arrays.asList("Identifier","OpenParen","StringLiteral","CloseParen","Semicolon","EOF")
        );

        List<List<String>> expectedValuesList = Arrays.asList(
            Arrays.asList("var","x","=","5",";","EOF"),
            Arrays.asList("CONST","Y","=","10",";","EOF"),
            Arrays.asList("var","z",";","EOF"),
            Arrays.asList("Y","=","11",";","EOF"),
            Arrays.asList("print","(","x",")",";","EOF"),
            Arrays.asList("func","add","(","a",",","b",")","{","a","+","b",";","}","EOF"),
            Arrays.asList("if","(","1",">","2",")","{","print","(","true",")",";","}","EOF"),
            Arrays.asList("elseif","(","1","==","2",")","{","print","(","true",")",";","}","EOF"),
            Arrays.asList("else","{","print","(","false",")",";","}","EOF"),
            Arrays.asList("print","(","\"Hello World\"",")",";","EOF")
        );

        // Run all tests
        for (int i = 0; i < tests.length; i++) {
            tests[i].setExpectedTypes(expectedTypesList.get(i));
            tests[i].setExpectedValues(expectedValuesList.get(i));

            try {
                System.out.print("Test " + (i + 1) + ": ");
                tests[i].runTest();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}