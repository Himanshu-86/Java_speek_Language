
package execution;
import java.util.*;
import parser.*;
import tokenizer.*;

public class Interpreter {

    public void run(String source) {
        Tokenizer tokenizer = new Tokenizer(source);
        List<Token> tokens = tokenizer.tokenize();

        Parser parser = new Parser(tokens);
        List<Instruction> instructions = parser.parse();

        Environment env = new Environment();

        for (Instruction inst : instructions) {
            inst.execute(env);
        }
    }
}
