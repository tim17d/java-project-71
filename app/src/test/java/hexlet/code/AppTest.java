package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    public void testCall() {
        assertThat(new App().call()).isEqualTo(0);
    }
}
