package hexlet.code;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

public class Data {
    public static ArrayList<LinkedHashMap<String, Object>> getDiffList(Map<String, Object> data1,
                                                                       Map<String, Object> data2) {
        var diffList = new ArrayList<LinkedHashMap<String, Object>>();
        var keysUnion = new TreeSet<>(data1.keySet());
        keysUnion.addAll(data2.keySet());
        keysUnion.forEach(key -> {
            if (!data1.containsKey(key)) {
                diffList.add(getStructuredData(key, "ADDED", data2.get(key)));
            } else if (!data2.containsKey(key)) {
                diffList.add(getStructuredData(key, "REMOVED", data1.get(key)));
            } else if (!Objects.equals(data1.get(key), data2.get(key))) {
                diffList.add(getStructuredDataWithChanges(key, "UPDATED", data1.get(key), data2.get(key)));
            } else {
                diffList.add(getStructuredData(key, "HELD", data1.get(key)));
            }
        });
        return diffList;
    }

    private static LinkedHashMap<String, Object> getStructuredData(String key, String type, Object value) {
        var data = new LinkedHashMap<String, Object>();
        data.put("key", key);
        data.put("type", type);
        data.put("value", value);
        return data;
    }

    private static LinkedHashMap<String, Object> getStructuredDataWithChanges(String key, String type, Object value1,
                                                                              Object value2) {
        var data = new LinkedHashMap<String, Object>();
        data.put("key", key);
        data.put("type", type);
        data.put("value1", value1);
        data.put("value2", value2);
        return data;
    }
}
