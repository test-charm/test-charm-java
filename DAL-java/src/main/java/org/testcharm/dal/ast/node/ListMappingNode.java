package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

import static org.testcharm.dal.runtime.ExpressionException.opt2;

public class ListMappingNode extends DALNode implements ExecutableNode {
    private final SymbolNode symbolNode;

    public ListMappingNode(DALNode symbolNode) {
        this.symbolNode = (SymbolNode) symbolNode;
        setPositionBegin(symbolNode.getPositionBegin());
    }

    @Override
    public String inspect() {
        return symbolNode.inspect() + "[]";
    }

    @Override
    public Data<?> getValue(Data<?> data, RuntimeContextBuilder.DALRuntimeContext context) {
        return new Data<>(opt2(data::list).autoMapping(d -> d.property(symbolNode.getRootSymbolName())),
                context, data.propertySchema(symbolNode.getRootSymbolName(), true));
    }

    @Override
    public Object getRootSymbolName() {
        return symbolNode.getRootSymbolName();
    }
}
