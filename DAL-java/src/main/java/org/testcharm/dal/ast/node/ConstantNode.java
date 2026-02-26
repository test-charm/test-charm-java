package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

public class ConstantNode extends DALNode {

    private final Object value;

    public ConstantNode(Object value) {
        this.value = value;
    }

    @Override
    public String inspect() {
        return "$" + value;
    }

    @Override
    public Data<?> evaluateData(RuntimeContextBuilder.DALRuntimeContext context) {
        return context.constants().property(value);
    }
}
