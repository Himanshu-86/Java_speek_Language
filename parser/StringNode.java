package parser;

import execution.Environment;

public class StringNode implements Expression {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    public Object evaluate(Environment env) {
        return value;
    }
}
