package org.testcharm.dal.ast.node;

import org.testcharm.dal.runtime.*;

import static org.testcharm.dal.runtime.DALException.locateError;

public class SchemaNode extends DALNode {
    private final String schema;

    public SchemaNode(String schema) {
        this.schema = schema;
    }

    @Override
    public String inspect() {
        return schema;
    }

    public Object convertViaSchema(RuntimeContextBuilder.DALRuntimeContext context, Data inputData, String inputProperty) {
        try {
            return context.searchValueConstructor(schema).orElseThrow(() -> locateError(
                            new DALRuntimeException("Schema '" + schema + "' not registered"), getPositionBegin()))
                    .apply(inputData, context);
        } catch (IllegalTypeException exception) {
            throw new AssertionFailure(exception.assertionFailureMessage(inputProperty.isEmpty() ?
                    inputProperty : inputProperty + " ", this), getPositionBegin());
        }
    }
}
