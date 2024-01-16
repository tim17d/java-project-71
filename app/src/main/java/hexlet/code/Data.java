package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Data {
    static Map<String, ArrayList<String>> getDiffMap(Map<String, Object> map1, Map<String, Object> map2) {
        var diffMap = new HashMap<>(Map.of(
                "created", new ArrayList<String>(),
                "deleted", new ArrayList<String>(),
                "changed", new ArrayList<String>()
        ));

        var created = new HashSet<>(map2.keySet());
        created.removeAll(map1.keySet());
        created.forEach(key -> diffMap.get("created").add(key));

        var deleted = new HashSet<>(map1.keySet());
        deleted.removeAll(map2.keySet());
        deleted.forEach(key -> diffMap.get("deleted").add(key));

        map1.keySet().stream()
                .filter(map2::containsKey)
                .forEach(key -> {
                    if (!Objects.equals(map1.get(key), map2.get(key))) {
                        diffMap.get("changed").add(key);
                    }
                });

        return diffMap;
    }
}
