package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0-SNAPSHOT",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Option(names = { "-f", "--format" }, paramLabel = "format", description = "output format [default: stylish]")
    private String format = "stylish";

    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private String filePath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    private String filePath2;

    @Override
    public final Integer call() {
        try {
            System.out.println(Differ.generate(filePath1, filePath2, format));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        var exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
