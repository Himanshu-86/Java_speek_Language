package parser;

import execution.Environment;

// Represents a variable in the AST
// Example: x, y, total
public class VariableNode implements Expression {

    private final String name;  // variable name

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public Object evaluate(Environment env) {
        // Fetches the value of the variable from environment (symbol table)
        return env.get(name);
    }
}