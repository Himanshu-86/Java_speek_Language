/**
 * Entry point for the Speek Interpreter.
 * Reads a source file path from command-line arguments
 * and runs the interpreter.
 */
import execution.Interpreter;
import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        try {
            //  Check if argument is provided
            if (args.length == 0) {
                System.out.println("Usage: java Main <source-file>");
                return;
            }

            //  Read file from argument
            String filePath = args[0];
            String code = Files.readString(Path.of(filePath));

            //  Run interpreter
            Interpreter interpreter = new Interpreter();
            interpreter.run(code);

        } catch (NoSuchFileException e) {
            System.out.println("Error: File not found - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}