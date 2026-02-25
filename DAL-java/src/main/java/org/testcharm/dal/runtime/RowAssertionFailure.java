package org.testcharm.dal.runtime;

import org.testcharm.dal.ast.node.TableNode;
import org.testcharm.dal.ast.node.TransposedTableNode;
import org.testcharm.interpreter.InterpreterException;

public class RowAssertionFailure extends java.lang.RuntimeException {
    protected final int indexSkipEllipsis;
    protected final DALException dalException;

    public RowAssertionFailure(int indexSkipEllipsis, DALException dalException) {
        this.indexSkipEllipsis = indexSkipEllipsis;
        this.dalException = dalException;
    }

    public DALException linePositionException(TableNode tableNode) {
        dalException.setType(InterpreterException.Position.Type.ROW);
        return dalException;
    }

    public DALException columnPositionException(TransposedTableNode transposedTableNode) {
        dalException.clearPosition();
        return transposedTableNode.transpose().fetchDataRowSkipEllipsis(indexSkipEllipsis).markPositionOnCells(dalException);
    }
}
