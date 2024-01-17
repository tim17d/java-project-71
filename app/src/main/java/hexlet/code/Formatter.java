package hexlet.code;

import hexlet.code.exceptions.FormatException;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.ArrayList;
import java.util.Map;

public class Formatter {
    public static String formatDiff(String format, Map<String, Object> data1, Map<String, Object> data2,
                                    Map<String, ArrayList<String>> diffMap) throws FormatException {
        if (format.equals("stylish")) {
            return StylishFormatter.formatDiff(data1, data2, diffMap);
        } else if (format.equals("plain")) {
            return PlainFormatter.formatDiff(data1, data2, diffMap);
        } else {
            throw new FormatException("Wrong format. Expected 'stylish' or 'plain'");
        }
    }
}
