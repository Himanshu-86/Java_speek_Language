
package parser;

import execution.Environment;

public interface Expression {
    Object evaluate(Environment env);
}
