package org.testcharm.dal.ast;

import org.testcharm.dal.DAL;
import org.testcharm.dal.ast.node.ConstValueNode;
import org.testcharm.dal.ast.node.ObjectScopeNode;
import org.testcharm.dal.ast.opt.DALOperator;
import org.testcharm.dal.ast.opt.Factory;
import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.testcharm.dal.ast.node.DALExpression.expression;
import static org.assertj.core.api.Assertions.assertThat;

class ObjectScopeNodeTest {

    public static final DALOperator equal = Factory.equal();

    @Nested
    class EqualTo {
        RuntimeContextBuilder.DALRuntimeContext DALRuntimeContext = new DAL().extend().getRuntimeContextBuilder().build(null);
        ObjectScopeNode objectScopeNode = new ObjectScopeNode(Collections.emptyList());

        @Test
        void empty_data_equal_to_empty_object() {
            HashMap<Object, Object> emptyMap = new HashMap<>();
            assertThat(expression(new ConstValueNode(emptyMap), equal, objectScopeNode).evaluate(DALRuntimeContext)).isSameAs(emptyMap);
        }
    }
}