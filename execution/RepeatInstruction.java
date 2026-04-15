package execution;

import java.util.List;
import parser.Expression;

/**
 * RepeatInstruction — handles loop / repeat statements.
 *
 * Represents a statement of the form:
 *     repeat <count> { <body> }
 *
 * Example Speek source:
 *     repeat 3 {
 *         print "hello"
 *     }
 *     repeat n {
 *         x = x + 1
 *     }
 *
 * How it works at runtime:
 *   1. The count Expression is evaluated to get the number of iterations.
 *      Speek numbers are stored as Double internally, so the value is cast
 *      Double → int via an intermediate (double) cast before truncation.
 *   2. The body instruction list is executed count times, in order, using
 *      the same Environment on every iteration (variables accumulate).
 *
 * Note on the double→int cast:
 *   (int)((double) count.evaluate(env))
 *   The inner (double) unboxes the Object to a primitive double.
 *   The outer (int) truncates it to a whole number.
 *   e.g.  3.0 → 3,  2.9 → 2  (no rounding — be careful with float counts).
 *
 * Created by the Parser when it sees:  REPEAT  <expr>  LBRACE  <instructions>  RBRACE
 */
public class RepeatInstruction implements Instruction {

    /**
     * Expression that evaluates to the number of times the body should run.
     * Must evaluate to a Double; fractional values are truncated to int.
     */
    private final Expression count;

    /**
     * The list of instructions that form the loop body.
     * Re-executed on every iteration; instructions see updated variable
     * values from previous iterations.
     */
    private final List<Instruction> body;

    /**
     * Constructs a RepeatInstruction.
     *
     * @param count  expression for the iteration count (e.g. literal 5 or variable n)
     * @param body   the block of instructions to loop over
     */
    public RepeatInstruction(Expression count, List<Instruction> body) {
        this.count = count;
        this.body = body;
    }

    /**
     * Evaluates the count and runs the body that many times.
     *
     * @param env  the runtime environment; state changes made inside the body
     *             (e.g. incrementing a counter variable) persist across iterations
     *             because there is no new scope per iteration.
     */
    @Override
    public void execute(Environment env) {
        // Evaluate count expression and convert Double → int for loop bound.
        int times = (int) ((double) count.evaluate(env));

        for (int i = 0; i < times; i++) {
            // Execute each instruction in the body for this iteration.
            for (Instruction inst : body) {
                inst.execute(env);
            }
        }
    }
}