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
        }
    }


    private String readNumber() {
        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && Character.isDigit(source.charAt(pos))) {
            sb.append(source.charAt(pos++));
        }

        return sb.toString();
    }