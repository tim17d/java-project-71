package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class PlainFormatter {
    public static String formatDiff(ArrayList<LinkedHashMap<String, Object>> diffList) {
        var sj = new StringJoiner("\n");
        diffList.forEach(property -> {
            if (property.get("type").equals("ADDED")) {
                sj.add("Property '" + property.get("key")
                        + "' was added with value: " + getWrapped(property.get("value")));
            } else if (property.get("type").equals("REMOVED")) {
                sj.add("Property '" + property.get("key") + "' was removed");
            } else if (property.get("type").equals("UPDATED")) {
                sj.add("Property '" + property.get("key") + "' was updated. "
                        + "From " + getWrapped(property.get("value1")) + " to " + getWrapped(property.get("value2")));
            }
        });
        return sj.toString();
    }

    private static Object getWrapped(Object value) {
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        } else if (value instanceof String) {
            return "'" + value + "'";
        }
        return value;
    }
}
