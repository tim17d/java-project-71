package hexlet.code;

import hexlet.code.exceptions.FormatException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DifferTest {
    private static final String RESOURCES_DIR = "src/test/resources/";

    @Test
    public void testGenerateWithAllTypeOfDiffs() throws Exception {
        var actualDiff1 = Differ.generate("stylish", RESOURCES_DIR + "file1.json",
                RESOURCES_DIR + "file2.json");
        var expectedDiff1 = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        assertThat(actualDiff1).as("File difference").isEqualTo(expectedDiff1);
    }

    @Test
    public void testGenerateWithAbsoluteDiff() throws Exception {
        var actualDiff2 = Differ.generate("stylish", RESOURCES_DIR + "file1.json",
                RESOURCES_DIR + "file3.json");
        var expectedDiff2 = """
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
        assertThat(actualDiff2).as("File difference").isEqualTo(expectedDiff2);
    }

    @Test
    public void testGenerateWithNoDiff() throws Exception {
        var actualDiff3 = Differ.generate("stylish", RESOURCES_DIR + "file1.json",
                RESOURCES_DIR + "file1.json");
        var expectedDiff3 = """
                {
                    follow: false
                    host: hexlet.io
                    proxy: 123.234.53.22
                    timeout: 50
                }""";
        assertThat(actualDiff3).as("File difference").isEqualTo(expectedDiff3);
    }

    @Test
    public void testGenerateThrowsExceptions() {
        assertThatThrownBy(() -> {
            Differ.generate("1234", RESOURCES_DIR + "file1.json", RESOURCES_DIR + "file2.json");
        }).isInstanceOf(FormatException.class)
                .hasMessage("Wrong format");
    }
}
