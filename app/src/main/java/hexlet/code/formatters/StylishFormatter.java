package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.StringJoiner;

public class StylishFormatter {
    public static String formatDiff(Map<String, Object> data1, Map<String, Object> data2,
                                    Map<String, ArrayList<String>> diffMap) {
        var keysUnion = new HashSet<>(data1.keySet());
        keysUnion.addAll(data2.keySet());
        var sj = new StringJoiner("\n  ");
        keysUnion.stream()
                .sorted()
                .forEach(key -> {
                    if (diffMap.get("added").contains(key)) {
                        sj.add("+ " + key + ": " + data2.get(key));
                    } else if (diffMap.get("removed").contains(key)) {
                        sj.add("- " + key + ": " + data1.get(key));
                    } else if (diffMap.get("updated").contains(key)) {
                        sj.add("- " + key + ": " + data1.get(key));
                        sj.add("+ " + key + ": " + data2.get(key));
                    } else {
                        sj.add("  " + key + ": " + data1.get(key));
                    }
                });
        return "{\n  " + sj + "\n}";
    }
}
