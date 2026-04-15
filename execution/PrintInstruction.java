package execution;

import parser.Expression;

/**
 * PrintInstruction — handles print / output statements.
 *
 * Represents a statement of the form:
 *     print <expression>
 *
 * Example Speek source:
 *     print 42
 *     print x + 1
 *     print "hello"
 *
 * How it works at runtime:
 *   The expression is evaluated against the current Environment and the
 *   resulting value is written to standard output via System.out.println().
 *   Because the value is typed as Object, Java's toString() gives a sensible
 *   default for both Double (e.g. "3.0") and Boolean ("true"/"false").
 *
 * Created by the Parser when it sees the keyword:  PRINT  <expr>
 */
public class PrintInstruction implements Instruction {

    /**
     * The expression to evaluate and print.
     * Stored as an AST node; evaluation happens at execute() time, not parse time.
     */
    private final Expression expr;

    /**
     * Constructs a PrintInstruction.
     *
     * @param expr  the expression whose value should be printed
     */
    public PrintInstruction(Expression expr) {
        this.expr = expr;
    }

    /**
     * Evaluates the expression and prints the result to stdout.
     *
     * @param env  the current runtime environment used to resolve variables
     *             referenced inside the expression
     */
    @Override
    public void execute(Environment env) {
        // evaluate() returns Object (Double or Boolean); println handles toString.
        System.out.println(expr.evaluate(env));
    }
}