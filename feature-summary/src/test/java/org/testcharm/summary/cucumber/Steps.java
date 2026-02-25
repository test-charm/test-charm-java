package org.testcharm.summary.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.testcharm.util.JavaExecutor;

public class Steps {

    @Before
    public void importDependencies() {
        JavaExecutor.executor().main().importDependency("org.testcharm.jfactory.*");
    }

    @Given("the following spec definition:")
    public void theFollowingSpecDefinition(String sourceCode) {
        JavaExecutor.executor().addClass(
                "import org.testcharm.jfactory.Spec;\n" +
                        "import org.testcharm.jfactory.Trait;\n" + sourceCode);
    }
}
