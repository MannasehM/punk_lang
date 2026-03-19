package src.parsing.lexer;

public class Token {
    private String value;
    private String type;

    public Token(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        String result = "{value: " + value + ", type: " + type + "}";
        return result;
    }
}