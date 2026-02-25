package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.DALRuntimeException;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.testcharm.interpreter.Node;

import static org.testcharm.dal.runtime.DALException.locateError;
import static org.testcharm.dal.runtime.ExpressionException.opt1;

public interface ExecutableNode extends Node<RuntimeContextBuilder.DALRuntimeContext, DALNode> {

    Data<?> getValue(Data<?> data, RuntimeContextBuilder.DALRuntimeContext context);

    default Data<?> getValue(DALNode left, RuntimeContextBuilder.DALRuntimeContext context) {
        return getValue(evaluateInput(left, context), context);
    }

    default Data<?> evaluateInput(DALNode left, RuntimeContextBuilder.DALRuntimeContext context) {
        Data<?> data = left.evaluateData(context);
        if (opt1(data::isNull))
            throw locateError(new DALRuntimeException("The instance of the property is null"), getOperandPosition());
        return data;
    }
}
