package org.testcharm.dal.ast.node;

import org.testcharm.dal.ast.node.table.RowType;
import org.testcharm.dal.ast.opt.DALOperator;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder.DALRuntimeContext;
import org.testcharm.interpreter.InterpreterException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testcharm.dal.ast.node.DALExpression.expression;
import static org.testcharm.dal.ast.node.table.Body.EMPTY_TABLE_ROW_TYPE;
import static java.util.stream.Collectors.toList;

public class GroupExpression extends DALNode {
    private final List<DALNode> group = new ArrayList<>();
    final List<DALNode> expressions = new ArrayList<>();
    private final String inspect;

    public GroupExpression(List<DALNode> group) {
        this.group.addAll(group);
        expressions.addAll(group);
        inspect = this.group.stream().map(DALNode::inspect).collect(Collectors.joining(", ", "<<", ">>"));
    }

    public GroupExpression(List<DALNode> group, List<DALNode> expressions, String inspect) {
        this.group.addAll(group);
        this.expressions.addAll(expressions);
        this.inspect = inspect;
    }

    @Override
    public Object evaluate(DALRuntimeContext context) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < expressions.size(); i++)
            result.add(evaluateExpression(context, expressions.get(i), i));
        return result;
    }

    private Object evaluateExpression(DALRuntimeContext context, DALNode expression, int index) {
        try {
            return expression.evaluate(context);
        } catch (InterpreterException e) {
            throw e.multiPosition(group.get(index).getOperandPosition(),
                    InterpreterException.Position.Type.CHAR);
        }
    }

    @Override
    public String inspect() {
        return inspect;
    }

    public DALNode append(DALOperator operator, DALNode right) {
        return new GroupExpression(group, expressions.stream()
                .map(e -> expression(e, operator, right)).collect(toList()),
                operator.inspect(inspect, right.inspect()));
    }

    public DALNode insert(DALNode left, DALOperator operator) {
        return new GroupExpression(group, expressions.stream()
                .map(e -> expression(left, operator, e)).collect(toList()),
                operator.inspect(left.inspect(), inspect));
    }

    @Override
    public Stream<Object> collectFields(Data<?> data) {
        return expressions.stream().map(e -> data.firstFieldFromAlias(e.getRootSymbolName()));
    }

    @Override
    public RowType guessTableHeaderType() {
        RowType rowType = EMPTY_TABLE_ROW_TYPE;
        for (DALNode expression : expressions)
            rowType = rowType.merge(expression.guessTableHeaderType());
        return rowType;
    }

    @Override
    public boolean needPrefixBlankWarningCheck() {
        return true;
    }
}
