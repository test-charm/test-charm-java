package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.RuntimeContextBuilder;

public class DataRemarkNode extends DALNode {
    private final String remark;

    public DataRemarkNode(String remark) {
        this.remark = remark;
    }

    @Override
    public String inspect() {
        return remark;
    }

    @Override
    public Object evaluate(RuntimeContextBuilder.DALRuntimeContext context) {
        return remark;
    }

    @Override
    public boolean needPrefixBlankWarningCheck() {
        return true;
    }
}
