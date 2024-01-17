package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Data {
    public static Map<String, ArrayList<String>> getDiffMap(Map<String, Object> map1, Map<String, Object> map2) {
        var diffMap = new HashMap<>(Map.of(
                "added", new ArrayList<String>(),
                "removed", new ArrayList<String>(),
                "updated", new ArrayList<String>()
        ));

        var created = new HashSet<>(map2.keySet());
        created.removeAll(map1.keySet());
        created.forEach(key -> diffMap.get("added").add(key));

        var deleted = new HashSet<>(map1.keySet());
        deleted.removeAll(map2.keySet());
        deleted.forEach(key -> diffMap.get("removed").add(key));

        map1.keySet().stream()
                .filter(map2::containsKey)
                .forEach(key -> {
                    if (!Objects.equals(map1.get(key), map2.get(key))) {
                        diffMap.get("updated").add(key);
                    }
                });

        return diffMap;
    }
}
