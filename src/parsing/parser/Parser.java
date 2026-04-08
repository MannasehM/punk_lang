import java.util.*;
import src.parsing.lexer.Lexer;
import src.parsing.lexer.Token;

public class Parser {
    private List<Token> tokens;

    public Parser() { /** nothing here */ }

    public boolean isEOF() {
        return tokens.get(0).getType().equals("EOF");
    }

    public Token currToken() {
        return tokens.get(0);
    }

    public Token nextToken() {
        return tokens.remove(0);
    }

    public void expect(String type, String message) throws Exception {
        Token prev = nextToken();
        if (prev == null || prev.getType() != type) {
            throw new Exception(message);
        }
    }

    public Node produceAST(String sourceCode) {
        Lexer lexer = new Lexer();
        try {
            tokens = lexer.tokenize(sourceCode);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        

        Node program = new Node("Program");

        while (!isEOF()) {
            program.getChildren().add(parseStatement());
        }

        return program;
    }

    public Node parseStatement() {
        switch(currToken().getType()) {
            case "Var":
                // parseVar()
            case "CONST":
                // parseConstant()
            case "Function":
                // parseFunction()
            case "IfStatement":
                // parseIfStatement()
            case "ElseIfStatement":
                // parseElseIfStatement()
            case "ElseStatement":
                // parseElseStatement()
            default: 
                // parseExpr() 
        }
        return null; // placeholder
    }
}