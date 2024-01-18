package hexlet.code;

import hexlet.code.exceptions.FormatException;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.ArrayList;
import java.util.Map;

public class Formatter {
    public static String formatDiff(String format, Map<String, Object> data1, Map<String, Object> data2,
                                    Map<String, ArrayList<String>> diffMap) throws FormatException {
        return switch (format) {
            case "stylish" -> StylishFormatter.formatDiff(data1, data2, diffMap);
            case "plain" -> PlainFormatter.formatDiff(data1, data2, diffMap);
            case "json" -> JsonFormatter.formatDiff(data1, data2, diffMap);
            default -> throw new FormatException("Wrong format. Expected 'stylish', 'plain' or 'json'");
        };
    }
}
