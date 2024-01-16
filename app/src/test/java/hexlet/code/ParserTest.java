package hexlet.code;

import hexlet.code.exceptions.FileException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParserTest {
    private static final String RESOURCES_DIR = "src/test/resources/";
    private static final Map<String, Object> EXPECTED_DATA = Map.of(
            "host", "hexlet.io",
            "timeout", 50,
            "proxy", "123.234.53.22",
            "follow", false
    );

    @Test
    public void testGetDataFromFileJson() throws Exception {
        var actualData = Parser.getDataFromFile(RESOURCES_DIR + "file1.json");
        assertThat(actualData).as("Data from JSON file").isEqualTo(EXPECTED_DATA);
    }

    @Test
    public void testGetDataFromFileYaml() throws Exception {
        var actualData = Parser.getDataFromFile(RESOURCES_DIR + "file1.yml");
        assertThat(actualData).as("Data from YAML file").isEqualTo(EXPECTED_DATA);
    }

    @Test
    public void testGetDataFromFileThrowsExceptions() {
        var filePath = "text.json";
        assertThatThrownBy(() -> {
            Parser.getDataFromFile(filePath);
        }).isInstanceOf(FileException.class)
                .hasMessage("File '" + filePath + "' does not exist");

        assertThatThrownBy(() -> {
            Parser.getDataFromFile(RESOURCES_DIR + "hello.html");
        }).isInstanceOf(FileException.class);
    }
}
