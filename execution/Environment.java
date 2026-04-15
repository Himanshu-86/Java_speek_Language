package execution;

import java.util.*;

/**
 * Environment — the runtime variable store for a single Speek program run.
 *
 * Think of it as the "memory" of the interpreter. Every time a variable is
 * assigned (e.g.  x = 5) the value is stored here, and every time an
 * expression references a variable its value is looked up here.
 *
 * Current design is intentionally flat (no scope nesting / closures), which
 * is fine for the subset of Speek that is currently supported.  If block-level
 * scoping is added later, Environment can be refactored into a linked chain of
 * maps (child → parent).
 *
 * Thread-safety: NOT thread-safe. One Environment instance per interpreter run.
 */
public class Environment {

    /**
     * The backing store: variable name → value.
     * Values are typed as Object so the environment can hold both
     * Double (all Speek numbers) and Boolean (comparison results).
     */
    private final Map<String, Object> vars = new HashMap<>();

    /**
     * Creates or overwrites a variable binding.
     *
     * @param name   the variable name exactly as it appears in source code
     * @param value  the evaluated value to bind (Double or Boolean)
     */
    public void set(String name, Object value) {
        vars.put(name, value);
    }

    /**
     * Retrieves the value bound to a variable name.
     *
     * @param name  the variable name to look up
     * @return      the value previously stored by set()
     * @throws RuntimeException if the variable has never been assigned —
     *         this surfaces as a runtime error to the user ("Variable not
     *         defined: x") rather than a silent null / NullPointerException.
     */
    public Object get(String name) {
        if (!vars.containsKey(name)) {
            throw new RuntimeException("Variable not defined: " + name);
        }
        return vars.get(name);
    }
}

