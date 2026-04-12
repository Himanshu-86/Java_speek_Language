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

    @Override
    public Object evaluate(Environment env) {
        double l = (double) left.evaluate(env);
        double r = (double) right.evaluate(env);

        return switch (op) {
            case "+" -> l + r;
            case "-" -> l - r;
            case "*" -> l * r;
            case "/" -> l / r;
            case ">" -> l > r;
            default -> throw new RuntimeException("Unknown op");
        };
    }
}
