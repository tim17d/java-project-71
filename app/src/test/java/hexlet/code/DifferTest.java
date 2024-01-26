package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DifferTest {
    private static final String RESOURCES_DIR = "src/test/resources/";
    private static final String EXPECTED_DIFF_STYLISH;
    private static final String EXPECTED_DIFF_PLAIN;
    private static final String EXPECTED_DIFF_JSON;

    static {
        try {
            EXPECTED_DIFF_STYLISH = Files.readString(Path.of(RESOURCES_DIR + "diff_stylish.txt"));
            EXPECTED_DIFF_PLAIN = Files.readString(Path.of(RESOURCES_DIR + "diff_plain.txt"));
            EXPECTED_DIFF_JSON = Files.readString(Path.of(RESOURCES_DIR + "diff_json.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGenerateJsonFilesDefaultFormat() throws Exception {
        var actualDiff = Differ.generate(RESOURCES_DIR + "nested1.json",
                RESOURCES_DIR + "nested2.json");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_STYLISH);
    }

    @Test
    public void testGenerateJsonFilesStylishFormat() throws Exception {
        var actualDiff = Differ.generate(RESOURCES_DIR + "nested1.json",
                RESOURCES_DIR + "nested2.json", "stylish");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_STYLISH);
    }

    @Test
    public void testGenerateJsonFilesPlainFormat() throws Exception {
        var actualDiff = Differ.generate(RESOURCES_DIR + "nested1.json",
                RESOURCES_DIR + "nested2.json", "plain");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_PLAIN);
    }

    @Test
    public void testGenerateJsonFilesJsonFormat() throws Exception {
        var actualDiff = Differ.generate(RESOURCES_DIR + "nested1.json",
                RESOURCES_DIR + "nested2.json", "json");
        JSONAssert.assertEquals(actualDiff, EXPECTED_DIFF_JSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testGenerateYamlFilesDefaultFormat() throws Exception {
        var actualDiff = Differ.generate(RESOURCES_DIR + "nested1.yml",
                RESOURCES_DIR + "nested2.yml");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_STYLISH);
    }

    @Test
    public void testGenerateYamlFilesStylishFormat() throws Exception {
        var actualDiff = Differ.generate(RESOURCES_DIR + "nested1.yml",
                RESOURCES_DIR + "nested2.yml", "stylish");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_STYLISH);
    }

    @Test
    public void testGenerateYamlFilesPlainFormat() throws Exception {
        var actualDiff = Differ.generate(RESOURCES_DIR + "nested1.yml",
                RESOURCES_DIR + "nested2.yml", "plain");
        assertThat(actualDiff).as("File difference").isEqualTo(EXPECTED_DIFF_PLAIN);
    }

    @Test
    public void testGenerateYamlFilesJsonFormat() throws Exception {
        var actualDiff = Differ.generate(RESOURCES_DIR + "nested1.yml",
                RESOURCES_DIR + "nested2.yml", "json");
        JSONAssert.assertEquals(actualDiff, EXPECTED_DIFF_JSON, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testGenerateThrowsException() {
        assertThatThrownBy(() -> {
            Differ.generate(RESOURCES_DIR + "nested1.json", RESOURCES_DIR + "nested2.json", "1234");
        }).isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Wrong format. Expected 'stylish', 'plain' or 'json'");

        assertThatThrownBy(() -> {
            Differ.generate("text.json", RESOURCES_DIR + "nested2.json");
        }).isInstanceOf(IOException.class);
    }
}
