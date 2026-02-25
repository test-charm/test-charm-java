package org.testcharm.dal.ast;

import org.testcharm.dal.DAL;
import org.testcharm.dal.ast.node.ConstValueNode;
import org.testcharm.dal.ast.node.DALExpression;
import org.testcharm.dal.ast.node.ListScopeNode;
import org.testcharm.dal.ast.opt.DALOperator;
import org.testcharm.dal.ast.opt.Factory;
import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListScopeNodeTest {

    public static final DALOperator equal = Factory.equal();
    public static final DALOperator match = Factory.match();
    RuntimeContextBuilder.DALRuntimeContext DALRuntimeContext = new DAL().extend().getRuntimeContextBuilder().build(null);
    ListScopeNode listScopeNode = new ListScopeNode(Collections.emptyList());

    @Test
    void empty_list_equal_to_or_matches_empty_list() {
        List<Object> emptyList = Collections.emptyList();
        assertThat(DALExpression.expression(new ConstValueNode(emptyList), equal, listScopeNode).evaluate(DALRuntimeContext)).isSameAs(emptyList);
        assertThat(DALExpression.expression(new ConstValueNode(emptyList), match, listScopeNode).evaluate(DALRuntimeContext)).isSameAs(emptyList);
    }
}