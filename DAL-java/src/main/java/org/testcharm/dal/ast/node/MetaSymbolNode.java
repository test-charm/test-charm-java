package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.testcharm.dal.runtime.SchemaType;

import static org.testcharm.dal.runtime.DALException.locateError;

public class MetaSymbolNode extends SymbolNode {
    public MetaSymbolNode(String content) {
        super(content, Type.SYMBOL);
    }

    @Override
    public Data<?> getValue(DALNode left, RuntimeContextBuilder.DALRuntimeContext context) {
        Data<?> inputData = context.lazy(() -> left.evaluateData(context).value(), SchemaType.create(null));
        try {
            return context.invokeMetaProperty(left, inputData, getRootSymbolName());
        } catch (Throwable e) {
            throw locateError(e, getPositionBegin());
        }
    }
}
