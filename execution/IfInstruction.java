package execution;

import parser.Expression;

import java.util.List;

public class IfInstruction implements Instruction {
    private final Expression condition;
    private final List<Instruction> body;

    public IfInstruction(Expression condition, List<Instruction> body) {
        this.condition = condition;
        this.body = body;
    }

    public void execute(Environment env) {
        Object result = condition.evaluate(env);

        if ((boolean) result) {
            for (Instruction inst : body) {
                inst.execute(env);
            }
        }
    }
}