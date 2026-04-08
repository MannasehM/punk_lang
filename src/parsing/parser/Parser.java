import java.util.*;
import src.parsing.lexer.Lexer;
import src.parsing.lexer.Token;
import src.parsing.parser.Node;

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

    public Node expect(String type, String message) {
        Token prev = nextToken();
        if (prev == null || prev.getType() != type) {
            throw new Exception(message);
        }
        return prev;
    }

    // need parseStatement()
    // need parseFloat()
    // need parseExpr()

    public Node produceAST(String sourceCode) {
        Lexer lexer = new Lexer();
        tokens = tokenize(sourceCode);

        Node program = new Node("Program");

        while (!isEOF()) {
            program.getChildren().add(parseStatement());
        }

        return program;
    }

    public Node parsePrimaryExpr() {
        String tokenType = tokens.currToken().getType();

        switch (tokenType) {
            case "Identifer": 
                return new Node("Identifier", nextToken().getValue());
            case "Number": 
                return new Node("NumericLiteral", parseFloat(nextToken().getValue()));
            case "StringLiteral": 
                String value = nextToken().getValue();
                // getting rid of quotation marks
                String newVal = value.substring(1, value.length() - 1);
                return new Node("StringLiteral", newVal);
            case "OpenParen": 
                nextToken(); // move to next token after the '('
                Node node = parseExpr();
                // to move past the ')' token
                expect("CloseParen", "Missing closing parentheses");
                return node;
            default: 
                throw new Exception("Unexpected token found during parsing: " + tokens.currToken().toString());
        }
    }
}