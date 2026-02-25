package org.testcharm.dal.ast.node;

import org.testcharm.dal.ast.opt.DALOperator;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder.DALRuntimeContext;

public class WildcardNode extends DALNode {
    private final String code;

    public WildcardNode(String code) {
        this.code = code;
    }

    @Override
    public String inspect() {
        return code;
    }

    @Override
    public Data<?> verify(DALOperator operator, DALNode actual, DALRuntimeContext context) {
        return context.data(true);
    }
}
