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
            
            // System.out.println("src.length(): " + src.length());
            // for (int i = 0; i < tokens.size(); i++) {
            //     System.out.println("token: " + tokens.get(i));  
            // }
            // System.out.println("----------------------");

            // PARSING ONE CHARACTER TOKENS
            if (charEquals(src.charAt(0), "(")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "OpenParen"));
                src.deleteCharAt(0);
            }
            else if (charEquals(src.charAt(0), ")")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "CloseParen"));
                src.deleteCharAt(0);
            } 
            else if (charEquals(src.charAt(0), "{")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "OpenCurly"));
                src.deleteCharAt(0);
            } 
            else if (charEquals(src.charAt(0), "}")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "CloseCurly"));
                src.deleteCharAt(0);
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
                tokens.add(getToken(String.valueOf(src.charAt(0)), "BinaryOperator"));
                src.deleteCharAt(0);
            }
            // handle assignment, logical and conditional tokens
            else if (charEquals(src.charAt(0), "=") && charEquals(src.charAt(1), "=")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)) + String.valueOf(src.charAt(1)), "ComparisonOperator"));
                src.deleteCharAt(0);
                src.deleteCharAt(1);
            }
            else if (charEquals(src.charAt(0), ">") && charEquals(src.charAt(1), "=")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)) + String.valueOf(src.charAt(1)), "ComparisonOperator"));
                src.deleteCharAt(0);
                src.deleteCharAt(1);
            }
            else if (charEquals(src.charAt(0), "<") && charEquals(src.charAt(1), "=")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)) + String.valueOf(src.charAt(1)), "ComparisonOperator"));
                src.deleteCharAt(0);
                src.deleteCharAt(1);
            }
            else if (charEquals(src.charAt(0), "!") && charEquals(src.charAt(1), "=")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)) + String.valueOf(src.charAt(1)), "ComparisonOperator"));
                src.deleteCharAt(0);
                src.deleteCharAt(1);
            }
            else if (charEquals(src.charAt(0), ">")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "ComparisonOperator"));
                src.deleteCharAt(0);
            }
            else if (charEquals(src.charAt(0), "<")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "ComparisonOperator"));
                src.deleteCharAt(0);
            }
            else if (charEquals(src.charAt(0), "!")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "UnaryOperator"));
                src.deleteCharAt(0);
            } 
            else if (charEquals(src.charAt(0), "&") && charEquals(src.charAt(1), "&")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)) + String.valueOf(src.charAt(1)), "LogicalOperator"));
                src.deleteCharAt(0);
                src.deleteCharAt(1);
            }
            else if (charEquals(src.charAt(0), "|") && charEquals(src.charAt(1), "|")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)) + String.valueOf(src.charAt(1)), "LogicalOperator"));
                src.deleteCharAt(0);
                src.deleteCharAt(1);
            }
            else if (charEquals(src.charAt(0), "=")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "Equals"));
                src.deleteCharAt(0);
            }
            else if (charEquals(src.charAt(0), ";")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "Semicolon"));
                src.deleteCharAt(0);
            } 
            else if (charEquals(src.charAt(0), ":")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "Colon"));
                src.deleteCharAt(0);
            } 
            else if (charEquals(src.charAt(0), ",")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "Comma"));
                src.deleteCharAt(0);
            } 
            else if (charEquals(src.charAt(0), ".")) {
                tokens.add(getToken(String.valueOf(src.charAt(0)), "Period"));
                src.deleteCharAt(0);
            } 
            // HANDLE MULTICHARACTER TOKENS
            else {
                // Handle String literals
                if (isQuotation(String.valueOf(src.charAt(0)))) {
                    String stringLiteral = String.valueOf(src.charAt(0));
                    src.deleteCharAt(0);

                    while (src.length() > 0 && !isQuotation(String.valueOf(src.charAt(0)))) {
                        stringLiteral += String.valueOf(src.charAt(0));
                        src.deleteCharAt(0);
                    }

                    if (src.length() > 0) {
                        stringLiteral += String.valueOf(src.charAt(0));
                        src.deleteCharAt(0);
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
                    String stringLiteral = String.valueOf(src.charAt(0));
                    src.deleteCharAt(0);

                    while (src.length() > 0 && !isApostrophe(String.valueOf(src.charAt(0)))) {
                        stringLiteral += String.valueOf(src.charAt(0));
                        src.deleteCharAt(0);
                    }

                    if (src.length() > 0) {
                        stringLiteral += String.valueOf(src.charAt(0));
                        src.deleteCharAt(0);
                    }
                    else {
                        throw new Exception("Missing closing single quote");
                    }

                    tokens.add(getToken(stringLiteral, "StringLiteral"));
                }
                else if (isInt(String.valueOf(src.charAt(0)))) {
                    String num = "";
                    while (src.length() > 0 && isInt(String.valueOf(src.charAt(0)))) {
                        num += String.valueOf(src.charAt(0));
                        src.deleteCharAt(0);
                    }

                    tokens.add(getToken(num, "Number"));
                }
                else if (isAlpha(String.valueOf(src.charAt(0)))) {
                    String identifier = "";
                    while (src.length() > 0 && isAlpha(String.valueOf(src.charAt(0)))) {
                        identifier += String.valueOf(src.charAt(0));
                        src.deleteCharAt(0);
                    }

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
                    throw new Exception("Unrecognized character found in source: " + src.charAt(0));
                }
            }
        }
        tokens.add(getToken("EOF", "EOF"));
        return tokens;
    }
}