
import execution.Interpreter;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String code = Files.readString(Path.of("test.txt"));

        Interpreter interpreter = new Interpreter();
        interpreter.run(code);
    }
}
