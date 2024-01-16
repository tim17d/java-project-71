package hexlet.code;

import hexlet.code.exceptions.FormatException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DifferTest {
    private static final String RESOURCES_DIR = "src/test/resources/";
    private static final String EXPECTED_DIFF = """
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

    @Test
    public void testGenerateWithAllTypeOfDiffsJson() throws Exception {
        var actualDiff = Differ.generate("stylish", RESOURCES_DIR + "nested1.json",
                RESOURCES_DIR + "nested2.json");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF);
    }

    @Test
    public void testGenerateWithAllTypeOfDiffsYaml() throws Exception {
        var actualDiff = Differ.generate("stylish", RESOURCES_DIR + "nested1.yml",
                RESOURCES_DIR + "nested2.yml");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF);
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
        }).isInstanceOf(FormatException.class)
                .hasMessage("Wrong format");
    }
}
