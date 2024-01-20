package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public class Parser {
    public static Map<String, Object> getDataFromContentString(String content, String contentType) throws IOException {
        ObjectMapper objectMapper;
        if (contentType.equals("json")) {
            objectMapper = new ObjectMapper();
        } else if (contentType.equals("yml")) {
            objectMapper = new YAMLMapper();
        } else {
            throw new UnsupportedOperationException("Wrong content type. Expected 'json' or 'yml'");
        }
        return objectMapper.readValue(content, new TypeReference<>() { });
    }
}
