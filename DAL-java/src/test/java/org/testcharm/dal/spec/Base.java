package org.testcharm.dal.spec;

import org.testcharm.dal.DAL;
import org.testcharm.dal.ast.node.DALNode;
import org.testcharm.dal.ast.node.SymbolNode;
import org.testcharm.dal.compiler.Notations;
import org.testcharm.dal.runtime.DALException;

import static org.testcharm.dal.ast.node.DALExpression.expression;
import static org.testcharm.dal.ast.opt.Factory.executable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Base {
    protected DAL dal = new DAL().extend();

    public static DALNode createPropertyNode(DALNode instanceNode, Object name) {
        return expression(instanceNode, executable(Notations.EMPTY), new SymbolNode(name, SymbolNode.Type.BRACKET));
    }

    protected void assertPass(Object input, String expression) {
        dal.evaluate(input, expression);
    }

    protected DALException assertFailed(Object input, String expression) {
        DALException dalException = null;
        try {
            dal.evaluate(input, expression);
        } catch (DALException failure) {
            dalException = failure;
        }
        assertThat(dalException).isNotNull();
        return dalException;
    }

    protected void assertRuntimeException(Object input, String sourceCode, int position, String message) {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> dal.evaluate(input, sourceCode));

        assertThat(runtimeException)
                .hasFieldOrPropertyWithValue("position", position)
                .hasMessage(message);
    }

    protected void assertErrorContains(Object input, String expression, String errorMessage) {
        assertThat(assertThrows(DALException.class, () -> {
            dal.evaluate(input, expression);
        })).hasMessage(errorMessage);
    }
}
