package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0-SNAPSHOT",
        description = "Compares two configuration files and shows a difference.")
public class App implements Runnable {

    @Option(names = { "-f", "--format" }, paramLabel = "format", description = "output format [default: stylish]")
    String format;

    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    String filePath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    String filePath2;

    @Override
    public void run() {
        System.out.println("Hello World!");
    }

    public static void main(String[] args) {
        var exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
