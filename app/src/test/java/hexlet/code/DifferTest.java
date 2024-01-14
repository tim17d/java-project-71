package hexlet.code;

import hexlet.code.exceptions.FileException;
import hexlet.code.exceptions.FormatException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DifferTest {
    String resourcesDir = "src/test/resources/";

    @Test
    public void testGenerate() throws Exception {
        var actualDiff1 = Differ.generate("stylish", resourcesDir + "file1.json",
                resourcesDir + "file2.json");
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

        var actualDiff2 = Differ.generate("stylish", resourcesDir + "file1.json",
                resourcesDir + "file3.json");
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

        var actualDiff3 = Differ.generate("stylish", resourcesDir + "file1.json",
                resourcesDir + "file1.json");
        var expectedDiff3 = """
                {
                    follow: false
                    host: hexlet.io
                    proxy: 123.234.53.22
                    timeout: 50
                }""";
        assertThat(actualDiff3).as("File difference").isEqualTo(expectedDiff3);

        assertThatThrownBy(() -> {
            Differ.generate("1234", resourcesDir + "file1.json", resourcesDir + "file2.json");
        }).isInstanceOf(FormatException.class)
                .hasMessageContaining("Wrong format");

        var filePath1 = "text1.json";
        assertThatThrownBy(() -> {
            Differ.generate("stylish", filePath1, resourcesDir + "file2.json");
        }).isInstanceOf(FileException.class)
                .hasMessage("File '" + filePath1 + "' does not exist");

        var filePath2 = "text2.json";
        assertThatThrownBy(() -> {
            Differ.generate("stylish", resourcesDir + "file1.json", filePath2);
        }).isInstanceOf(FileException.class)
                .hasMessage("File '" + filePath2 + "' does not exist");
    }
}
