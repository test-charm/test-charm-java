package org.testcharm.jfactory.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.testcharm.util.JavaExecutor;

public class Steps {

    @Before
    public void importDependencies() {
        JavaExecutor.executor().importDependency("java.util.List");
        JavaExecutor.executor().importDependency("java.util.HashMap");
        JavaExecutor.executor().main().importDependency("org.testcharm.jfactory.*");
    }

    @Given("the following spec definition:")
    public void theFollowingSpecDefinition(String sourceCode) {
        JavaExecutor.executor().addClass(
                "import org.testcharm.jfactory.Spec;\n" +
                        "import org.testcharm.jfactory.Global;\n" +
                        "import org.testcharm.jfactory.Instance;\n" +
                        "import org.testcharm.jfactory.Trait;\n" + sourceCode);
    }
}
