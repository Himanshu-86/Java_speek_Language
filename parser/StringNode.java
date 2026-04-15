package parser;

import execution.Environment;

// Represents a string literal in the AST
// Example: "hello", "x is greater than y"
public class StringNode implements Expression {

    private final String value;  // stores the string content

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Environment env) {
        // Returns the string directly (no computation needed)
        return value;
    }
}