package tokenizer;

import java.util.*;

public class Tokenizer {
    private final String source;
    private int pos = 0;
    private int line = 1;

    public Tokenizer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < source.length()) {
            char c = source.charAt(pos);

            if (Character.isWhitespace(c)) {
                if (c == '\n') {
                    tokens.add(new Token(TokenType.NEWLINE, "\n", line++));
                }
                pos++;
            }

            else if (Character.isDigit(c)) {
                String num = readNumber();
                tokens.add(new Token(TokenType.NUMBER, num, line));
            }

            else if (Character.isLetter(c)) {
                String word = readWord();

                if (word.equals("is")) {
                    skipWhitespace();

                    String next1 = readWord(); // greater
                    skipWhitespace();

                    String next2 = readWord(); // than

                    if (next1.equals("greater") && next2.equals("than")) {
                        tokens.add(new Token(TokenType.GREATER_THAN, ">", line));
                    } else {
                        throw new RuntimeException("Invalid comparison syntax");
                    }
                } else {
                    tokens.add(new Token(getKeyword(word), word, line));
                }
            }

            else if (c == '"') {
                String str = readString();
                tokens.add(new Token(TokenType.STRING, str, line));
            }

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

            else {
                throw new RuntimeException("Unexpected char: " + c);
            }
        }

        tokens.add(new Token(TokenType.EOF, "", line));
        return tokens;
        }
    }

    private String readNumber() {
        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && Character.isDigit(source.charAt(pos))) {
            sb.append(source.charAt(pos++));
        }

        return sb.toString();
    }

    private String readWord() {
        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && Character.isLetter(source.charAt(pos))) {
            sb.append(source.charAt(pos++));
        }

        return sb.toString();
    }

    private String readString() {
        pos++;

        StringBuilder sb = new StringBuilder();

        while (source.charAt(pos) != '"') {
            sb.append(source.charAt(pos++));
        }

        pos++;
        return sb.toString();
    }

    private void skipWhitespace() {
        while (pos < source.length() && Character.isWhitespace(source.charAt(pos))) {
            pos++;
        }
    }

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