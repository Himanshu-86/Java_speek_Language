package parser;

import execution.Environment;

// Represents any evaluatable expression in the language
// All expression nodes (like numbers, variables, binary ops) implement this
public interface Expression {

    // Evaluates the expression using current environment (variables, values)
    // Returns Object because result can be number or boolean
    Object evaluate(Environment env);
}