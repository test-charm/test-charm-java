package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

public class PropertyThis extends DALNode implements ExecutableNode {

    @Override
    public String inspect() {
        return "{}";
    }

    @Override
    public Data<?> getValue(Data<?> data, RuntimeContextBuilder.DALRuntimeContext context) {
        return data;
    }

    @Override
    public Data<?> evaluateInput(DALNode left, RuntimeContextBuilder.DALRuntimeContext context) {
        return left.evaluateData(context);
    }
}
