package parser;

import execution.Environment;

// Represents a binary operation node in the AST
// Example: x + y, a * b, x > y
public class BinaryOpNode implements Expression {

    private final Expression left;   // left side expression
    private final Expression right;  // right side expression
    private final String op;         // operator (+, -, *, /, >)

    public BinaryOpNode(Expression left, String op, Expression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public Object evaluate(Environment env) {

        // Evaluate left and right expressions using current environment
        double l = (double) left.evaluate(env);
        double r = (double) right.evaluate(env);

        // Perform operation based on operator
        switch (op) {

            case "+":
                return l + r;   // addition

            case "-":
                return l - r;   // subtraction

            case "*":
                return l * r;   // multiplication

            case "/":
                return l / r;   // division

            case ">":
                return l > r;   // comparison (returns boolean)

            default:
                // If operator is not recognized
                throw new RuntimeException("Unknown op");
        }
    }
}