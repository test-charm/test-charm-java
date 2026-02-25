package org.testcharm.cucumber;

import org.junit.jupiter.api.Test;

import static io.cucumber.core.cli.Main.run;
import static org.assertj.core.api.Assertions.assertThat;

public class RunCucumberRequiresAddOpens {
    @Test
    void run_cucumber() {
        assertThat(run("--plugin", "pretty", "--glue", "org.testcharm",
                "src/test/resources/features/requires-add-opens")).isEqualTo(Byte.valueOf("0"));
    }
}
