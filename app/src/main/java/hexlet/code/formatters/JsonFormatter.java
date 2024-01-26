package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JsonFormatter {
    public static String formatDiff(ArrayList<LinkedHashMap<String, Object>> diffList) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(diffList);
    }
}
