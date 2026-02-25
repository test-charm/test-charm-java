package org.testcharm.jfactory.cucumber;

import org.junit.jupiter.api.Test;

import static io.cucumber.core.cli.Main.run;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcharm.jfactory.cucumber.IntegrationTestContextLegacy.threadsCount;

class Run {

    @Test
    void run_cucumber() {
        assertThat(run("--plugin", "pretty", "--glue", "org.testcharm", "--threads",
                String.valueOf(threadsCount("COMPILER_THREAD_SIZE", 8)),
                "src/test/resources/features/")).isEqualTo(Byte.valueOf("0"));
    }
}
