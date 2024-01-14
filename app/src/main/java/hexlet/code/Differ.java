package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.exceptions.FileException;
import hexlet.code.exceptions.FormatException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringJoiner;

public class Differ {
    public static String generate(String format, String filePath1, String filePath2)
            throws FileException, FormatException {
        var map1 = getDataFromFile(filePath1);
        var map2 = getDataFromFile(filePath2);
        var diffMap = getDiffMap(map1, map2);
        if (format.equals("stylish")) {
            var keysUnion = new HashSet<>(map1.keySet());
            keysUnion.addAll(map2.keySet());
            var sj = new StringJoiner("\n  ");
            keysUnion.stream()
                    .sorted()
                    .forEach(key -> {
                        if (diffMap.get("created").contains(key)) {
                            sj.add("+ " + key + ": " + map2.get(key));
                        } else if (diffMap.get("deleted").contains(key)) {
                            sj.add("- " + key + ": " + map1.get(key));
                        } else if (diffMap.get("changed").contains(key)) {
                            sj.add("- " + key + ": " + map1.get(key));
                            sj.add("+ " + key + ": " + map2.get(key));
                        } else {
                            sj.add("  " + key + ": " + map1.get(key));
                        }
                    });
            return "{\n  " + sj + "\n}";
        } else {
            throw new FormatException("Wrong format");
        }
    }

    private static Map<String, Object> getDataFromFile(String filePath) throws FileException {
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new FileException("File '" + filePath + "' does not exist");
        }
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(Files.readString(path), new TypeReference<>() { });
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    private static Map<String, ArrayList<String>> getDiffMap(Map<String, Object> map1, Map<String, Object> map2) {
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
                    if (!map1.get(key).equals(map2.get(key))) {
                        diffMap.get("changed").add(key);
                    }
                });

        return diffMap;
    }
}
