package hexlet.code;

import hexlet.code.exceptions.FileException;
import hexlet.code.exceptions.FormatException;

import java.util.HashSet;
import java.util.StringJoiner;

public class Differ {
    public static String generate(String format, String filePath1, String filePath2)
            throws FileException, FormatException {
        var map1 = Parser.getDataFromFile(filePath1);
        var map2 = Parser.getDataFromFile(filePath2);
        var diffMap = Data.getDiffMap(map1, map2);
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
}
