package org.testcharm.dal.extensions.jfactory;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testcharm.dal.DAL;
import org.testcharm.dal.extensions.Beans;
import org.testcharm.dal.extensions.Orders;
import org.testcharm.jfactory.JFactory;
import org.testcharm.jfactory.cucumber.JData;
import org.testcharm.util.JavaExecutor;

import static org.testcharm.dal.Assertions.expect;

public class Steps {

    private static final JFactory jFactory = new JFactory() {{
        register(Orders.class);
        register(Beans.class);
    }};
    private static final JData jdata = new JData(jFactory);

    @Before
    public void reset() {
        jFactory.getDataRepository().clear();
    }

    @Given("{string}:")
    public void givenData(String spec, io.cucumber.datatable.DataTable dataTable) {
        jdata.prepare(spec, dataTable.asMaps());
    }

    @Then("query data by jfactory:")
    public void query_data_by_jfactory(String dal) {
        expect(jFactory).should(dal);
    }

    @Then("dumped jfactoy should be:")
    public void dumped_jfactoy_should_be(String docString) {
        expect(DAL.getInstance().getRuntimeContextBuilder().build(jFactory).getThis().dump()).should(docString);
    }


    @Before
    public void importDependencies() {
        JavaExecutor.executor().main().importDependency("org.testcharm.jfactory.*");
        JavaExecutor.executor().main().importDependency("org.testcharm.dal.*");
    }

    @Given("the following spec definition:")
    public void theFollowingSpecDefinition(String sourceCode) {
        JavaExecutor.executor().addClass(
                "import org.testcharm.jfactory.Spec;\n" +
                        "import org.testcharm.jfactory.Global;\n" +
                        "import org.testcharm.jfactory.Instance;\n" +
                        "import org.testcharm.jfactory.Trait;\n" + sourceCode);
    }

    @When("{string} collect and build with the following properties:")
    public void collectAndBuildWithTheFollowingProperties(String collectorVarName,
                                                          String expressionForBuild) {
        String expressionForBuildVarName = "exp";
        JavaExecutor.executor().main().addArg(expressionForBuildVarName, expressionForBuild);
        JavaExecutor.executor().main().addRegisters(String.format("Accessors.get((String)args.get(\"%s\")).from(%s)", expressionForBuildVarName, collectorVarName));
        JavaExecutor.executor().main().returnExpression(collectorVarName + ".build()");
    }

    @When("{string} collect with the following properties:")
    public void collectWithTheFollowingProperties(String collectorVarName,
                                                  String expressionForBuild) {
        String expressionForBuildVarName = "exp";
        JavaExecutor.executor().main().addArg(expressionForBuildVarName, expressionForBuild);
        JavaExecutor.executor().main().addRegisters(String.format("Accessors.get((String)args.get(\"%s\")).from(%s)", expressionForBuildVarName, collectorVarName));
        JavaExecutor.executor().main().returnExpression(collectorVarName);
    }
}
