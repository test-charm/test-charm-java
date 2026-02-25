package org.testcharm.dal.ast.node.table;

import org.testcharm.dal.ast.node.InputNode;
import org.testcharm.dal.ast.node.SortGroupNode;
import org.testcharm.dal.ast.node.SymbolNode;
import org.testcharm.dal.ast.opt.Factory;
import org.testcharm.dal.compiler.Notations;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

import static org.testcharm.dal.ast.node.DALExpression.expression;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

public class DefaultIndexColumnHeaderRow extends ColumnHeaderRow {

    public DefaultIndexColumnHeaderRow() {
        super(emptyList());
    }

    @Override
    public ColumnHeader getHeader(int index, RuntimeContextBuilder.DALRuntimeContext context) {
        return new ColumnHeader(SortGroupNode.NO_SEQUENCE, expression(new InputNode.StackInput(context),
                Factory.executable(Notations.EMPTY), new SymbolNode(index, SymbolNode.Type.NUMBER)), empty());
    }

    @Override
    public void checkDataCellSize(Row rowNode) {
    }

    @Override
    public String inspect() {
        return "^";
    }
}
