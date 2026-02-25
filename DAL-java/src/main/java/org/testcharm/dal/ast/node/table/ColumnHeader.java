package org.testcharm.dal.ast.node.table;

import org.testcharm.dal.ast.node.DALExpression;
import org.testcharm.dal.ast.node.DALNode;
import org.testcharm.dal.ast.node.SortGroupNode;
import org.testcharm.dal.ast.opt.DALOperator;
import org.testcharm.dal.compiler.DALProcedure;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder.DALRuntimeContext;
import org.testcharm.interpreter.OperatorParser;

import java.util.Comparator;
import java.util.Optional;

public class ColumnHeader extends DALNode {
    private final SortGroupNode sort;
    private final DALNode property;
    private final Optional<DALOperator> operator;

    public ColumnHeader(SortGroupNode sort, DALNode property, Optional<DALOperator> operator) {
        this.sort = sort;
        this.property = property;
        this.operator = operator;
    }

    @Override
    public String inspect() {
        String property = this.property.inspect();
        return sort.inspect() + operator.map(operator -> operator.inspect(property, "").trim()).orElse(property);
    }

    public DALNode property() {
        return property;
    }

    public OperatorParser<DALRuntimeContext, DALNode, DALOperator, DALProcedure, DALExpression> operator() {
        return procedure -> operator;
    }

    public Comparator<Data<?>> comparator(DALRuntimeContext context) {
        return sort.comparator(data -> data.execute(() -> context.transformComparable(property.evaluate(context))));
    }

    public static Comparator<ColumnHeader> bySequence() {
        return Comparator.comparing(headerNode -> headerNode.sort, SortGroupNode.comparator().reversed());
    }
}
