package tokenizer;
// Enum representing all possible token types in our language
// Helps the parser understand what kind of element it is dealing with
public enum TokenType {
    LET, BE, SAY, IF, THEN, REPEAT, TIMES,
    GREATER_THAN,
    NUMBER, STRING, IDENTIFIER,
    PLUS, MINUS, MULTIPLY, DIVIDE,
    NEWLINE, EOF
}
