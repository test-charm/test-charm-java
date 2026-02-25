package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

import static org.testcharm.dal.runtime.ExpressionException.opt2;

public class ListMappingNodeMeta extends ListMappingNode {
    public ListMappingNodeMeta(DALNode symbolNode) {
        super(symbolNode);
    }

    @Override
    public Data<?> getValue(DALNode left, RuntimeContextBuilder.DALRuntimeContext context) {
        return context.data(opt2(left.evaluateData(context)::list).autoMapping(item ->
                context.invokeMetaProperty(left, item, getRootSymbolName())));
    }
}
