package execution;

import java.util.List;
import parser.Expression;

public class RepeatInstruction implements Instruction {
    private final Expression count;
    private final List<Instruction> body;

    public RepeatInstruction(Expression count, List<Instruction> body) {
        this.count = count;
        this.body = body;
    }

    public void execute(Environment env) {
        int times = (int) ((double) count.evaluate(env));

        for (int i = 0; i < times; i++) {
            for (Instruction inst : body) {
                inst.execute(env);
            }
        }
    }
}