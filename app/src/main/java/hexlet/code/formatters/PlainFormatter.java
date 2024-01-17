package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class PlainFormatter {
    public static String formatDiff(Map<String, Object> data1, Map<String, Object> data2,
                                    Map<String, ArrayList<String>> diffMap) {
        var wrappedData1 = getWrappedData(data1);
        var wrappedData2 = getWrappedData(data2);
        var keysUnion = new HashSet<>(data1.keySet());
        keysUnion.addAll(data2.keySet());
        var sj = new StringJoiner("\n");
        keysUnion.stream()
                .sorted()
                .forEach(key -> {
                    if (diffMap.get("added").contains(key)) {
                        sj.add("Property '" + key + "' was added with value: " + wrappedData2.get(key));
                    } else if (diffMap.get("removed").contains(key)) {
                        sj.add("Property '" + key + "' was removed");
                    } else if (diffMap.get("updated").contains(key)) {
                        sj.add("Property '" + key + "' was updated. From " + wrappedData1.get(key)
                                + " to " + wrappedData2.get(key));
                    }
                });
        return sj.toString();
    }

    private static Map<String, Object> getWrappedData(Map<String, Object> data) {
        var wrappedData = new HashMap<>(data);
        wrappedData.replaceAll((key, value) -> {
            if (value instanceof List || value instanceof Map) {
                value = "[complex value]";
            } else if (value instanceof String) {
                value = "'" + value + "'";
            }
            return value;
        });
        return wrappedData;
    }
}
