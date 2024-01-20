package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ParserTest {
    private static final Map<String, Object> EXPECTED_DATA = Map.of(
            "host", "hexlet.io",
            "timeout", 50,
            "proxy", "123.234.53.22",
            "follow", false
    );
    private static final String JSON_CONTENT = """
            {
              "host": "hexlet.io",
              "timeout": 50,
              "proxy": "123.234.53.22",
              "follow": false
            }""";
    private static final String YML_CONTENT = """
            host: "hexlet.io"
            timeout: 50
            proxy: "123.234.53.22"
            follow: false""";
    private static final String HTML_CONTENT = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Hello World</title>
            </head>
            <body>
            <h1>Hello World!</h1>
            </body>
            </html>""";

    @Test
    public void testGetDataFromContentStringJson() throws Exception {
        var actualData = Parser.getDataFromContentString(JSON_CONTENT, "json");
        assertThat(actualData).as("Data from JSON file").isEqualTo(EXPECTED_DATA);
    }

    @Test
    public void testGetDataFromContentStringYaml() throws Exception {
        var actualData = Parser.getDataFromContentString(YML_CONTENT, "yml");
        assertThat(actualData).as("Data from YAML file").isEqualTo(EXPECTED_DATA);
    }

    @Test
    public void testGetDataFromContentStringThrowsExceptions() {
        assertThatThrownBy(() -> {
            Parser.getDataFromContentString(JSON_CONTENT, "html");
        }).isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Wrong content type. Expected 'json' or 'yml'");

        assertThatThrownBy(() -> {
            Parser.getDataFromContentString(HTML_CONTENT, "json");
        }).isInstanceOf(IOException.class);
    }
}
