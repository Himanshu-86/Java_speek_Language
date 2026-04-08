
package execution;

import parser.Expression;

public class PrintInstruction implements Instruction {
    private final Expression expr;

    public PrintInstruction(Expression expr) {
        this.expr = expr;
    }

    public void execute(Environment env) {
        System.out.println(expr.evaluate(env));
    }
}
