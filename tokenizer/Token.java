package tokenizer;

// Represents a single token produced by the tokenizer
// Each token has a type (what it is), value (actual text), and line (for errors)
public class Token {

    private final TokenType type;   // category of token (e.g., NUMBER, PLUS, IDENTIFIER)
    private final String value;     // actual content (e.g., "123", "+", "x")
    private final int line;         // line number where token appears (for debugging/errors)

    // Constructor to initialize token with its details
    public Token(TokenType type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }

    // Returns the type of token (used by parser to understand structure)
    public TokenType getType() { 
        return type; 
    }

    // Returns actual value of token (useful for identifiers, numbers, strings)
    public String getValue() { 
        return value; 
    }

    // Returns line number (helps in precise error reporting)
    public int getLine() { 
        return line; 
    }
}