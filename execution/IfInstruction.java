package execution;

import java.util.List;
import parser.Expression;

/**
 * IfInstruction — handles conditional (if) statements.
 *
 * Represents a statement of the form:
 *     if <condition> { <body> }
 *
 * Example Speek source:
 *     if x > 0 {
 *         print x
 *     }
 *
 * How it works at runtime:
 *   1. The condition Expression is evaluated — it must produce a Boolean.
 *   2. If the result is true, every Instruction in the body list is executed
 *      in order against the same Environment.
 *   3. If false, the entire block is skipped (no else branch currently).
 *
 * Note: There is an unchecked cast to (boolean) on the condition result.
 * If the condition expression returns a non-Boolean (e.g. a Double), this
 * will throw a ClassCastException at runtime. The parser is responsible for
 * ensuring conditions are boolean-valued expressions.
 *
 * Created by the Parser when it sees:  IF  <expr>  LBRACE  <instructions>  RBRACE
 */
public class IfInstruction implements Instruction {

    /**
     * The boolean condition that guards the body block.
     * Evaluated fresh on each execute() call.
     */
    private final Expression condition;

    /**
     * The list of instructions to execute when the condition is true.
     * May be empty (no-op if block), though the parser typically warns about that.
     */
    private final List<Instruction> body;

    /**
     * Constructs an IfInstruction.
     *
     * @param condition  a boolean-valued expression (e.g. x > 0)
     * @param body       the block of instructions to run when condition is true
     */
    public IfInstruction(Expression condition, List<Instruction> body) {
        this.condition = condition;
        this.body = body;
    }

    /**
     * Evaluates the condition and conditionally executes the body.
     *
     * @param env  the runtime environment shared with the body instructions;
     *             variables set inside the if-block are visible after it
     *             (no block-level scoping in the current design)
     */
    @Override
    public void execute(Environment env) {
        // Evaluate condition — expected to be a Boolean.
        Object result = condition.evaluate(env);

        if ((boolean) result) {
            // Condition is true: run each instruction in the body sequentially.
            for (Instruction inst : body) {
                inst.execute(env);
            }
        }
        // Condition is false: skip the block entirely (no else support yet).
    }
}