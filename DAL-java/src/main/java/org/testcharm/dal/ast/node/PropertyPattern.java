package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

public class PropertyPattern extends DALNode implements ExecutableNode {
    private final DALNode symbol;

    public PropertyPattern(DALNode symbol) {
        this.symbol = symbol;
    }

    @Override
    public String inspect() {
        return symbol.inspect() + "{}";
    }

    @Override
    public Data<?> getValue(Data<?> data, RuntimeContextBuilder.DALRuntimeContext context) {
        String prefix = symbol.getRootSymbolName().toString();
        Data<?> partial = data.filter(prefix);
        context.initPartialPropertyStack(data, prefix, partial);
        return partial;
    }
}
