package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.exceptions.FormatException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonFormatter {
    public static String formatDiff(Map<String, Object> data1, Map<String, Object> data2,
                                    Map<String, ArrayList<String>> diffMap) throws FormatException {
        var diffObject = new LinkedHashMap<String, ArrayList<LinkedHashMap<String, Object>>>();
        diffObject.put("added", new ArrayList<>());
        diffObject.put("removed", new ArrayList<>());
        diffObject.put("updated", new ArrayList<>());
        var keysUnion = new HashSet<>(data1.keySet());
        keysUnion.addAll(data2.keySet());
        keysUnion.stream()
                .sorted()
                .forEach(key -> {
                    if (diffMap.get("added").contains(key)) {
                        diffObject.get("added").add(getDataForJson(key, data2.get(key)));
                    } else if (diffMap.get("removed").contains(key)) {
                        diffObject.get("removed").add(getDataForJson(key, data1.get(key)));
                    } else if (diffMap.get("updated").contains(key)) {
                        var changes = new LinkedHashMap<String, Object>();
                        changes.put("from", data1.get(key));
                        changes.put("to", data2.get(key));
                        diffObject.get("updated").add(getDataForJson(key, changes));
                    }
                });
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(diffObject);
        } catch (JsonProcessingException e) {
            throw new FormatException("Unable to generate difference in JSON format");
        }
    }

    private static LinkedHashMap<String, Object> getDataForJson(String key, Object value) {
        var data = new LinkedHashMap<String, Object>();
        data.put("key", key);
        data.put("value", value);
        return data;
    }
}
