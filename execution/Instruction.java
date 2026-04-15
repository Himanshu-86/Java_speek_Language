package execution;

/**
 * Instruction — the core interface of the Speek interpreter's execution layer.
 *
 * Every statement in the Speek language (assignment, print, if, repeat) is
 * modelled as an Instruction. The interpreter builds a List<Instruction> from
 * the parsed AST and then calls execute() on each one in order.
 *
 * Design pattern: Command Pattern — each concrete instruction encapsulates
 * "what to do" and carries all the data it needs (expressions, sub-instruction
 * lists, etc.) so the caller (Interpreter) stays completely decoupled from the
 * details of each statement type.
 */
public interface Instruction {

    /**
     * Executes this instruction against the given runtime environment.
     *
     * @param env  the live variable store for this execution run; instructions
     *             read from and write to it as needed (e.g. AssignInstruction
     *             writes, PrintInstruction reads).
     */
    void execute(Environment env);
}