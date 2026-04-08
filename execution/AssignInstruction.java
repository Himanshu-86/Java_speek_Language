package execution;

import parser.Expression;

public class AssignInstruction implements Instruction {
    private final String name;
    private final Expression expr;

    public AssignInstruction(String name, Expression expr) {
        this.name = name;
        this.expr = expr;
    }

    public void execute(Environment env) {
        env.set(name, expr.evaluate(env));
    }
}
