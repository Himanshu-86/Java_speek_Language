
package execution;

import java.util.*;

public class Environment {
    private final Map<String, Object> vars = new HashMap<>();

    public void set(String name, Object value) {
        vars.put(name, value);
    }

    public Object get(String name) {
        if (!vars.containsKey(name)) {
            throw new RuntimeException("Variable not defined: " + name);
        }
        return vars.get(name);
    }
}
