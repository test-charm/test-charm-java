package org.testcharm.dal.ast.node.table;

import org.testcharm.dal.ast.node.*;
import org.testcharm.dal.ast.node.InputNode.StackInput;
import org.testcharm.dal.ast.opt.Factory;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder.DALRuntimeContext;
import org.testcharm.interpreter.Clause;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.testcharm.dal.ast.node.DALExpression.expression;
import static org.testcharm.dal.ast.node.SymbolNode.Type.BRACKET;
import static org.testcharm.dal.ast.node.table.SpecifyIndexRowType.indexToExpression;
import static org.testcharm.dal.compiler.Notations.EMPTY;
import static org.testcharm.util.function.When.when;

public abstract class RowType {

    public abstract RowType merge(RowType another);

    protected RowType mergeBy(SpecifyIndexRowType specifyIndexRowType) {
        throw new IllegalArgumentException();
    }

    protected RowType mergeBy(DefaultIndexRowType defaultIndexRowType) {
        throw new IllegalArgumentException();
    }

    protected RowType mergeBy(SpecifyPropertyRowType specifyPropertyRowType) {
        throw new IllegalArgumentException();
    }

    public abstract DALNode constructVerificationNode(Data<?> actual, Stream<Clause<DALNode>> rowClauses,
                                                      Comparator<Data<?>> comparator);

    public DALNode constructAccessingRowNode(DALNode input, Optional<DALNode> indexOrKey, DALRuntimeContext context) {
        return input;
    }
}

class EmptyTableRowType extends RowType {

    @Override
    public RowType merge(RowType another) {
        return another;
    }

    @Override
    protected RowType mergeBy(SpecifyIndexRowType specifyIndexRowType) {
        return this;
    }

    @Override
    protected RowType mergeBy(DefaultIndexRowType defaultIndexRowType) {
        return this;
    }

    @Override
    protected RowType mergeBy(SpecifyPropertyRowType specifyPropertyRowType) {
        return this;
    }

    @Override
    public DALNode constructVerificationNode(Data<?> actual, Stream<Clause<DALNode>> rowClauses,
                                             Comparator<Data<?>> comparator) {
        return actual.isList() ? new ListScopeNode(rowClauses.collect(toList()), comparator, ListScopeNode.Style.TABLE)
                : new ObjectScopeNode(Collections.emptyList());
    }
}

class SpecifyIndexRowType extends RowType {
    @Override
    public RowType merge(RowType another) {
        return another.mergeBy(this);
    }

    @Override
    protected RowType mergeBy(SpecifyIndexRowType specifyIndexRowType) {
        return specifyIndexRowType;
    }

    @Override
    protected RowType mergeBy(SpecifyPropertyRowType specifyPropertyRowType) {
        return specifyPropertyRowType;
    }

    @Override
    public DALNode constructVerificationNode(Data<?> actual, Stream<Clause<DALNode>> rowClauses,
                                             Comparator<Data<?>> comparator) {
        List<DALNode> rowNodes = rowClauses.map(rowClause -> rowClause.expression(null))
                .collect(toList());
        if (actual.isList())
            return new ListScopeNode(rowNodes, ListScopeNode.Type.FIRST_N_ITEMS, comparator, ListScopeNode.Style.TABLE);
        return new ObjectScopeNode(rowNodes);
    }

    @Override
    public DALNode constructAccessingRowNode(DALNode input, Optional<DALNode> indexOrKey, DALRuntimeContext context) {
        return indexOrKey.flatMap(node -> indexToExpression(node, context)).orElseThrow(IllegalStateException::new);
    }

    static Optional<DALNode> indexToExpression(DALNode node, DALRuntimeContext context) {
        return when(node instanceof ConstValueNode).optional(() -> expression(new StackInput(context), Factory.executable(EMPTY),
                new SymbolNode(((ConstValueNode) node).getValue(), BRACKET).setPositionBegin(node.getPositionBegin())));
    }
}

class DefaultIndexRowType extends RowType {
    @Override
    public RowType merge(RowType another) {
        return another.mergeBy(this);
    }

    @Override
    protected RowType mergeBy(DefaultIndexRowType defaultIndexRowType) {
        return defaultIndexRowType;
    }

    @Override
    public DALNode constructVerificationNode(Data<?> actual, Stream<Clause<DALNode>> rowClauses,
                                             Comparator<Data<?>> comparator) {
        return new ListScopeNode(rowClauses.collect(toList()), comparator, ListScopeNode.Style.TABLE);
    }
}

class SpecifyPropertyRowType extends RowType {

    @Override
    public RowType merge(RowType another) {
        return another.mergeBy(this);
    }

    @Override
    public DALNode constructVerificationNode(Data<?> actual, Stream<Clause<DALNode>> rowClauses,
                                             Comparator<Data<?>> comparator) {
        return new ObjectScopeNode(rowClauses.map(rowNode -> rowNode.expression(null)).collect(toList()));
    }

    @Override
    protected RowType mergeBy(SpecifyPropertyRowType specifyPropertyRowType) {
        return specifyPropertyRowType;
    }

    @Override
    protected RowType mergeBy(SpecifyIndexRowType specifyIndexRowType) {
        return this;
    }

    @Override
    public DALNode constructAccessingRowNode(DALNode input, Optional<DALNode> indexOrKey, DALRuntimeContext context) {
        return indexOrKey.map(node -> indexToExpression(node, context).orElse(node)).orElseThrow(IllegalStateException::new);
    }
}
