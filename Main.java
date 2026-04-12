
/**
 * Entry point for the Speek Interpreter.
 * Reads the 'test.txt' file and runs the interpreter.
 */
import execution.Interpreter;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Load the Speek source code from the test.txt file
        String code = Files.readString(Path.of("test.txt"));

        // Initialize and run the interpreter
        Interpreter interpreter = new Interpreter();
        interpreter.run(code);
    }
}
