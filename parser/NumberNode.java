package parser;

import execution.Environment;

// Represents a numeric literal in the AST
// Example: 5, 10, 3.14
public class NumberNode implements Expression {

    private final double value;  // stores the numeric value

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Environment env) {
        // Returns the number directly (no computation needed)
        return value;
    }
}