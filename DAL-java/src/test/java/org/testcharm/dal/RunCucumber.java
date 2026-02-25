package org.testcharm.dal;

import org.junit.jupiter.api.Test;
import org.testcharm.dal.cucumber.TestTask;

import static io.cucumber.core.cli.Main.run;
import static org.assertj.core.api.Assertions.assertThat;

public class RunCucumber {

    @Test
    void run_cucumber() {
        assertThat(run("--plugin", "pretty", "--glue", "org.testcharm", "--threads",
                String.valueOf(TestTask.threadsCount("COMPILER_THREAD_SIZE", 8)),
                "src/test/resources/features")).isEqualTo(Byte.valueOf("0"));
    }
}
