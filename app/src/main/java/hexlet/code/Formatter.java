package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.JsonFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Formatter {
    public static String formatDiff(String format, ArrayList<LinkedHashMap<String, Object>> diffList)
            throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> StylishFormatter.formatDiff(diffList);
            case "plain" -> PlainFormatter.formatDiff(diffList);
            case "json" -> JsonFormatter.formatDiff(diffList);
            default -> throw new UnsupportedOperationException("Wrong format. Expected 'stylish', 'plain' or 'json'");
        };
    }
}
