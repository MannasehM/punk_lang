package src.parsing.lexer;

import java.util.*;

public class Lexer {
    private HashMap<String, String> keywords;

    public Lexer() {
        keywords = new HashMap<String, String>();
        keywords.put("var", "Var");
        keywords.put("CONST", "Const");
        keywords.put("func", "Function");
        keywords.put("if", "IfStatement");
        keywords.put("elseif", "ElseIfStatement");
        keywords.put("else", "ElseStatement");
    }

    /**
     * Returns a token of a given type and value
     */
    public Token getToken(String value, String type) {
        Token token = new Token(value, type);
        return token;
    }

    /**
     * Returns whether character is alphabetic [a-zA-Z]
     */
    public boolean isAlpha(String s) {
        char c = s.charAt(0);
        return Character.isLetter(c);
    }

    /**
     * Returns whether character is whitespace [\s, \t, \n, \r]
     */
    public boolean isSkippable(String s) {
        if (s.equals(" ") || 
            s.equals("\n") || 
            s.equals("\t") || 
            s.equals("\r")
        ) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns whether the character is a valid integer (0-9)
     */
    public boolean isInt(String s) {
        // int ch = (int) s.charAt(0);
        // int[] bounds = new int {(int) "0".charAt(0), (int) "9".charAt(0)};
        // return ch >= bounds[0] && ch <= bounds[1];
        char c = s.charAt(0);
        return c >= '0' && c <= '9';
    }

    /**
     * Returns whether the character is a quotation
     */
    public boolean isQuotation(String s) {
        return s.equals("\"");
    }

    /**
     * Returns whether the character is a apostrophe
     */
    public boolean isApostrophe(String s) {
        return s.equals("'");
    }

    public boolean charEquals(char c, String s) {
        return String.valueOf(c).equals(s);
    }

    /**
     * Given a string, produce and return an list of tokens.
     */
    public List<Token> tokenize(String sourceCode) throws Exception {
        ArrayList<Token> tokens = new ArrayList<Token>();

        // converting string into mutable sequence so we can parse each character
        StringBuilder src = new StringBuilder(sourceCode);

        // produce tokens until EOF
        while (src.length() > 0) {
            
            for (int i = 0; i < tokens.size(); i++) {
                System.out.println("src.length(): " + src.length());
                System.out.println("token: " + tokens.get(i));
                System.out.println("----------------------");
            }

            // PARSING ONE CHARACTER TOKENS
            if (charEquals(src.charAt(0), "(")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "OpenParen"));
            }
            else if (charEquals(src.charAt(0), ")")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "CloseParen"));
            } 
            else if (charEquals(src.charAt(0), "{")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "OpenCurly"));
            } 
            else if (charEquals(src.charAt(0), "}")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "CloseCurly"));
            } 
            // handle binary operators
            else if (
                charEquals(src.charAt(0), "+") || 
                charEquals(src.charAt(0), "-") || 
                charEquals(src.charAt(0), "*") || 
                charEquals(src.charAt(0), "/") || 
                charEquals(src.charAt(0), "%") || 
                charEquals(src.charAt(0), "&&")
            ) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "BinaryOperator"));
            }
            // handle assignment, logical and conditional tokens
            else if (charEquals(src.charAt(0), "=") && charEquals(src.charAt(1), "=")) {
                tokens.add(getToken(src.deleteCharAt(0).toString() + src.deleteCharAt(1).toString(), "ComparisonOperator"));
            }
            else if (charEquals(src.charAt(0), ">") && charEquals(src.charAt(1), "=")) {
                tokens.add(getToken(src.deleteCharAt(0).toString() + src.deleteCharAt(1).toString(), "ComparisonOperator"));
            }
            else if (charEquals(src.charAt(0), "<") && charEquals(src.charAt(1), "=")) {
                tokens.add(getToken(src.deleteCharAt(0).toString() + src.deleteCharAt(1).toString(), "ComparisonOperator"));
            }
            else if (charEquals(src.charAt(0), "!") && charEquals(src.charAt(1), "=")) {
                tokens.add(getToken(src.deleteCharAt(0).toString() + src.deleteCharAt(1).toString(), "ComparisonOperator"));
            }
            else if (charEquals(src.charAt(0), ">")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "ComparisonOperator"));
            }
            else if (charEquals(src.charAt(0), "<")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "ComparisonOperator"));
            }
            else if (charEquals(src.charAt(0), "!")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "UnaryOperator"));
            } 
            else if (charEquals(src.charAt(0), "&") && charEquals(src.charAt(1), "&")) {
                tokens.add(getToken(src.deleteCharAt(0).toString() + src.deleteCharAt(1).toString(), "LogicalOperator"));
            }
            else if (charEquals(src.charAt(0), "|") && charEquals(src.charAt(1), "|")) {
                tokens.add(getToken(src.deleteCharAt(0).toString() + src.deleteCharAt(1).toString(), "LogicalOperator"));
            }
            else if (charEquals(src.charAt(0), "=")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "Equals"));
            }
            else if (charEquals(src.charAt(0), ";")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "Semicolon"));
            } 
            else if (charEquals(src.charAt(0), ":")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "Colon"));
            } 
            else if (charEquals(src.charAt(0), ",")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "Comma"));
            } 
            else if (charEquals(src.charAt(0), ".")) {
                tokens.add(getToken(src.deleteCharAt(0).toString(), "Period"));
            } 
            // HANDLE MULTICHARACTER TOKENS
            else {
                // Handle String literals
                if (isQuotation(String.valueOf(src.charAt(0)))) {
                    String stringLiteral = src.deleteCharAt(0).toString();
                    while (src.length() > 0 && !isQuotation(String.valueOf(src.charAt(0)))) {
                        stringLiteral += src.deleteCharAt(0).toString();
                    }

                    if (src.length() > 0) {
                        stringLiteral += src.deleteCharAt(0).toString();
                    }
                    else {
                        throw new Exception("Missing closing double quote");
                    }

                    tokens.add(getToken(stringLiteral, "StringLiteral"));
                    // if src is empty, break from loop
                    if (src.length() == 0) {
                        break;
                    }
                }

                if (isApostrophe(String.valueOf(src.charAt(0)))) {
                    String stringLiteral = src.deleteCharAt(0).toString();
                    while (src.length() > 0 && !isApostrophe(String.valueOf(src.charAt(0)))) {
                        stringLiteral += src.deleteCharAt(0).toString();
                    }

                    if (src.length() > 0) {
                        stringLiteral += src.deleteCharAt(0).toString();
                    }
                    else {
                        throw new Exception("Missing closing single quote");
                    }

                    tokens.add(getToken(stringLiteral, "StringLiteral"));
                }
                else if (isInt(String.valueOf(src.charAt(0)))) {
                    String num = "";
                    while (src.length() > 0 && isInt(String.valueOf(src.charAt(0)))) {
                        num += src.deleteCharAt(0).toString();
                    }

                    tokens.add(getToken(num, "Number"));
                }
                else if (isAlpha(String.valueOf(src.charAt(0)))) {

                    //System.out.println("first char: " + src.charAt(0));

                    String identifier = "";
                    while (src.length() > 0 && isAlpha(String.valueOf(src.charAt(0)))) {
                        //System.out.println("silly char: " + src.charAt(0));

                        identifier += String.valueOf(src.charAt(0));
                        src.deleteCharAt(0);
                    }

                    //System.out.println("Identifier: " + identifier);

                    // check for reserved keywords
                    if (keywords.get(identifier) != null) {
                        tokens.add(getToken(identifier, keywords.get(identifier)));
                    }
                    else {
                        tokens.add(getToken(identifier, "Identifier"));
                    }
                }
                else if (isSkippable(String.valueOf(src.charAt(0)))) {
                    src.deleteCharAt(0);
                }
                else {
                    System.out.println(src.length());
                    throw new Exception("Unrecognized character found in source: " + src.charAt(0));
                }
            }
        }
        tokens.add(getToken("EOF", "EOF"));
        return tokens;
    }
}