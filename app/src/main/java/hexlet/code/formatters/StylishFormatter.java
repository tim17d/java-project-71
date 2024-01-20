package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringJoiner;

public class StylishFormatter {
    public static String formatDiff(ArrayList<LinkedHashMap<String, Object>> diffList) {
        var sj = new StringJoiner("\n  ");
        diffList.forEach(property -> {
            if (property.get("type").equals("ADDED")) {
                sj.add("+ " + property.get("key") + ": " + property.get("value"));
            } else if (property.get("type").equals("REMOVED")) {
                sj.add("- " + property.get("key") + ": " + property.get("value"));
            } else if (property.get("type").equals("UPDATED")) {
                sj.add("- " + property.get("key") + ": " + property.get("value1"));
                sj.add("+ " + property.get("key") + ": " + property.get("value2"));
            } else {
                sj.add("  " + property.get("key") + ": " + property.get("value"));
            }
        });
        return "{\n  " + sj + "\n}";
    }
}
