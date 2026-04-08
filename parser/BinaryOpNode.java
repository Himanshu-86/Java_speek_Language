package parser;
import execution.Environment;

public class BinaryOpNode implements Expression {
    private final Expression left;
    private final Expression right;
    private final String op;

    public BinaryOpNode(Expression left, String op, Expression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public Object evaluate(Environment env) {
        double l = (double) left.evaluate(env);
        double r = (double) right.evaluate(env);

        switch (op) {
            case "+": return l + r;
            case "-": return l - r;
            case "*": return l * r;
            case "/": return l / r;
            case ">": return l > r;
            default: throw new RuntimeException("Unknown op");
        }
    }
}
