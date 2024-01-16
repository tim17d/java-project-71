package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.exceptions.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {
    public static Map<String, Object> getDataFromFile(String filePath) throws FileException {
        var path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new FileException("File '" + filePath + "' does not exist");
        }
        var splittedFilePath = filePath.split("\\.");
        var extension = splittedFilePath[splittedFilePath.length - 1];
        ObjectMapper objectMapper;
        if (extension.equals("json")) {
            objectMapper = new ObjectMapper();
        } else if (extension.equals("yml")) {
            objectMapper = new YAMLMapper();
        } else {
            throw new FileException("Wrong file extension");
        }
        try {
            return objectMapper.readValue(Files.readString(path), new TypeReference<>() { });
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }
}
