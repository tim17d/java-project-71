package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Differ {
    public static String generate(String format, String filePath1, String filePath2) throws IOException {
        var contentString1 = getContentStringFromFile(filePath1);
        var contentType1 = getContentTypeFromFile(filePath1);
        var data1 = Parser.getDataFromContentString(contentString1, contentType1);

        var contentString2 = getContentStringFromFile(filePath2);
        var contentType2 = getContentTypeFromFile(filePath2);
        var data2 = Parser.getDataFromContentString(contentString2, contentType2);

        var diffMap = Data.getDiffList(data1, data2);
        return Formatter.formatDiff(format, diffMap);
    }

    public static String generate(String filePath1, String filePath2) throws IOException {
        return generate("stylish", filePath1, filePath2);
    }

    private static String getContentStringFromFile(String filePath) throws IOException {
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new RuntimeException("File '" + filePath + "' does not exist");
        }
        return Files.readString(path);
    }

    private static String getContentTypeFromFile(String filePath) {
        var splittedFilePath = filePath.split("\\.");
        return splittedFilePath[splittedFilePath.length - 1];
    }
}
