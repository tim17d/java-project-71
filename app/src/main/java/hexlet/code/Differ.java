package hexlet.code;

import hexlet.code.exceptions.FileException;
import hexlet.code.exceptions.FormatException;

public class Differ {
    public static String generate(String format, String filePath1, String filePath2)
            throws FileException, FormatException {
        var data1 = Parser.getDataFromFile(filePath1);
        var data2 = Parser.getDataFromFile(filePath2);
        var diffMap = Data.getDiffMap(data1, data2);
        return Formatter.formatDiff(format, data1, data2, diffMap);
    }
}
