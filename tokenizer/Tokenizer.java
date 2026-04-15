package tokenizer;

import java.util.*;

// Custom exception to represent syntax-related errors in input
// Helps us give meaningful messages with line numbers
class SyntaxException extends RuntimeException {
    public SyntaxException(String message, int line) {
        super("Syntax Error at line " + line + ": " + message);
    }
}

public class Tokenizer {
    private final String source;  // full input string (user program)
    private int pos = 0;          // current index in the string
    private int line = 1;         // keeps track of line number for error reporting

    public Tokenizer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        // Loop through entire input character by character
        while (pos < source.length()) {
            char c = source.charAt(pos);

            // Handle spaces, tabs, and new lines
            // We track '\n' separately to maintain correct line count
            if (Character.isWhitespace(c)) {
                if (c == '\n') {
                    tokens.add(new Token(TokenType.NEWLINE, "\n", line));
                    line++;
                }
                pos++;
            }

            // If digit found → read full number (e.g., 123)
            // Keeps consuming digits until non-digit appears
            else if (Character.isDigit(c)) {
                String num = readNumber();
                tokens.add(new Token(TokenType.NUMBER, num, line));
            }

            // If letter found → could be keyword or identifier
            // Example: let, if, variableName
            else if (Character.isLetter(c)) {
                String word = readWord();

                // Special grammar: "is greater than"
                // Converts natural language into '>' operator
                if (word.equals("is")) {
                    skipWhitespace();

                    String next1 = readWordSafe("Expected 'greater' after 'is'");
                    skipWhitespace();
                    String next2 = readWordSafe("Expected 'than' after 'greater'");

                    // If pattern doesn't match exactly → syntax error
                    if (!next1.equals("greater") || !next2.equals("than")) {
                        throw new SyntaxException("Expected 'greater than' after 'is'", line);
                    }

                    tokens.add(new Token(TokenType.GREATER_THAN, ">", line));
                } else {
                    // Normal keyword or variable name
                    tokens.add(new Token(getKeyword(word), word, line));
                }
            }

            // Read string inside double quotes ("hello")
            // Ensures string is properly closed
            else if (c == '"') {
                String str = readString();
                tokens.add(new Token(TokenType.STRING, str, line));
            }

            // Handle arithmetic operators
            else if (c == '+') {
                tokens.add(new Token(TokenType.PLUS, "+", line));
                pos++;
            }

            else if (c == '-') {
                tokens.add(new Token(TokenType.MINUS, "-", line));
                pos++;
            }

            else if (c == '*') {
                tokens.add(new Token(TokenType.MULTIPLY, "*", line));
                pos++;
            }

            else if (c == '/') {
                tokens.add(new Token(TokenType.DIVIDE, "/", line));
                pos++;
            }

            else if (c == '>') {
                tokens.add(new Token(TokenType.GREATER_THAN, ">", line));
                pos++;
            }

            // If character doesn't match any known pattern → error
            // This prevents invalid symbols from being silently ignored
            else {
                throw new SyntaxException("Unexpected character '" + c + "'", line);
            }
        }

        // Add EOF token to mark end of input
        tokens.add(new Token(TokenType.EOF, "", line));
        return tokens;
    }

    // Reads continuous digits and forms a number token
    // Stops when a non-digit character is encountered
    private String readNumber() {
        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && Character.isDigit(source.charAt(pos))) {
            sb.append(source.charAt(pos++));
        }

        return sb.toString();
    }

    // Reads continuous letters to form a word
    // Used for keywords (let, if) and identifiers (variable names)
    private String readWord() {
        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && Character.isLetter(source.charAt(pos))) {
            sb.append(source.charAt(pos++));
        }

        return sb.toString();
    }

    // Safe version of readWord()
    // Throws error if expected word is missing (important for grammar rules)
    private String readWordSafe(String errorMessage) {
        if (pos >= source.length() || !Character.isLetter(source.charAt(pos))) {
            throw new SyntaxException(errorMessage, line);
        }
        return readWord();
    }

    // Reads characters inside quotes
    // Also ensures string is not left open (missing closing ")
    private String readString() {
        pos++; // skip opening quote

        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && source.charAt(pos) != '"') {
            // Prevent strings from spanning multiple lines
            if (source.charAt(pos) == '\n') {
                throw new SyntaxException("String cannot span multiple lines", line);
            }
            sb.append(source.charAt(pos++));
        }

        // If closing quote not found → error
        if (pos >= source.length()) {
            throw new SyntaxException("Unterminated string", line);
        }

        pos++; // skip closing quote
        return sb.toString();
    }

    // Skips whitespace (used in structured phrases like "is greater than")
    // Also updates line count if new lines are encountered
    private void skipWhitespace() {
        while (pos < source.length() && Character.isWhitespace(source.charAt(pos))) {
            if (source.charAt(pos) == '\n') {
                line++;
            }
            pos++;
        }
    }

    // Maps keywords to token types
    // If not a keyword → treated as identifier (variable name)
    private TokenType getKeyword(String word) {
        switch (word) {
            case "let": return TokenType.LET;
            case "be": return TokenType.BE;
            case "say": return TokenType.SAY;
            case "if": return TokenType.IF;
            case "then": return TokenType.THEN;
            case "repeat": return TokenType.REPEAT;
            case "times": return TokenType.TIMES;
            default: return TokenType.IDENTIFIER;
        }
    }
}