package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DifferTest {
    private static final String RESOURCES_DIR = "src/test/resources/";

    private static final String EXPECTED_DIFF_STYLISH = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

    private static final String EXPECTED_DIFF_PLAIN = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

    private static final String EXPECTED_DIFF_JSON;

    static {
        try {
            EXPECTED_DIFF_JSON = Files.readString(Path.of(RESOURCES_DIR + "nested_diff.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGenerateWithAllTypeOfDiffsJsonFormatStylish() throws Exception {
        var actualDiff = Differ.generate("stylish", RESOURCES_DIR + "nested1.json",
                RESOURCES_DIR + "nested2.json");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_STYLISH);
    }

    @Test
    public void testGenerateWithAllTypeOfDiffsYamlFormatStylish() throws Exception {
        var actualDiff = Differ.generate("stylish", RESOURCES_DIR + "nested1.yml",
                RESOURCES_DIR + "nested2.yml");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_STYLISH);
    }

    @Test
    public void testGenerateWithAllTypeOfDiffsJsonFormatPlain() throws Exception {
        var actualDiff = Differ.generate("plain", RESOURCES_DIR + "nested1.json",
                RESOURCES_DIR + "nested2.json");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_PLAIN);
    }

    @Test
    public void testGenerateWithAllTypeOfDiffsYamlFormatPlain() throws Exception {
        var actualDiff = Differ.generate("plain", RESOURCES_DIR + "nested1.yml",
                RESOURCES_DIR + "nested2.yml");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_PLAIN);
    }

    @Test
    public void testGenerateWithAllTypeOfDiffsJsonFormatJson() throws Exception {
        var actualDiff = Differ.generate("json", RESOURCES_DIR + "nested1.json",
                RESOURCES_DIR + "nested2.json");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_JSON);
    }

    @Test
    public void testGenerateWithAllTypeOfDiffsYamlFormatJson() throws Exception {
        var actualDiff = Differ.generate("json", RESOURCES_DIR + "nested1.yml",
                RESOURCES_DIR + "nested2.yml");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_JSON);
    }

    @Test
    public void testGenerateWithAbsoluteDiff() throws Exception {
        var actualDiff = Differ.generate("stylish", RESOURCES_DIR + "file1.json",
                RESOURCES_DIR + "file3.json");
        var expectedDiff = """
                {
                  + adapter: 123.234.53.22
                  + client: hexlet.io
                  - follow: false
                  - host: hexlet.io
                  + latency: 50
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + unfollow: false
                }""";
        assertThat(actualDiff).as("File difference").isEqualTo(expectedDiff);
    }

    @Test
    public void testGenerateWithNoDiff() throws Exception {
        var actualDiff = Differ.generate("stylish", RESOURCES_DIR + "file1.json",
                RESOURCES_DIR + "file1.json");
        var expectedDiff = """
                {
                    follow: false
                    host: hexlet.io
                    proxy: 123.234.53.22
                    timeout: 50
                }""";
        assertThat(actualDiff).as("File difference").isEqualTo(expectedDiff);
    }

    @Test
    public void testGenerateThrowsException() {
        assertThatThrownBy(() -> {
            Differ.generate("1234", RESOURCES_DIR + "file1.json", RESOURCES_DIR + "file2.json");
        }).isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Wrong format. Expected 'stylish', 'plain' or 'json'");

        var filePath1 = "text.json";
        assertThatThrownBy(() -> {
            Differ.generate("text.json", RESOURCES_DIR + "file2.json");
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("File '" + filePath1 + "' does not exist");

        assertThatThrownBy(() -> {
            Differ.generate(RESOURCES_DIR + "hello.html", RESOURCES_DIR + "file2.json");
        }).isInstanceOf(UnsupportedOperationException.class);
    }
}
