package execution;

import parser.Expression;

/**
 * AssignInstruction — handles variable assignment statements.
 *
 * Represents a statement of the form:
 *     <identifier> = <expression>
 *
 * Example Speek source:
 *     x = 10
 *     total = x + 5
 *
 * How it works at runtime:
 *   1. The right-hand side Expression is evaluated against the current
 *      Environment (this resolves any variable references and arithmetic).
 *   2. The resulting value is stored in the Environment under the given name,
 *      creating the variable if it didn't exist or overwriting it if it did.
 *
 * Created by the Parser when it sees:  IDENTIFIER  EQUALS  <expr>
 */
public class AssignInstruction implements Instruction {

    /** The variable name on the left-hand side of the assignment (e.g. "x"). */
    private final String name;

    /**
     * The expression on the right-hand side whose value will be stored.
     * This is an AST node — evaluation is deferred until execute() is called.
     */
    private final Expression expr;

    /**
     * Constructs an AssignInstruction.
     *
     * @param name  the target variable name
     * @param expr  the expression whose evaluated value will be assigned
     */
    public AssignInstruction(String name, Expression expr) {
        this.name = name;
        this.expr = expr;
    }

    /**
     * Evaluates the RHS expression and stores the result in the environment.
     *
     * @param env  the runtime variable store to write into
     */
    @Override
    public void execute(Environment env) {
        // Evaluate the expression first (may read other variables from env),
        // then bind the result to the target variable name.
        env.set(name, expr.evaluate(env));
    }
}

