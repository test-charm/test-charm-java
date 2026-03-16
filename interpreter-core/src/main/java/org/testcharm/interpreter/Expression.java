package org.testcharm.interpreter;

public interface Expression<C extends RuntimeContext, N extends Node<C, N>, E extends Expression<C, N, E, O>,
        O extends Operator<C, N, O, E>> extends Node<C, N> {

    N left();

    N right();

    O operator();

    @SuppressWarnings("unchecked")
    default N applyPrecedence(ExpressionFactory<C, N, E, O> factory) {
        if (left() instanceof Expression) {
            E leftExpression = (E) left();
            if (compareLeft(leftExpression))
                return (N) factory.create(leftExpression.left(), leftExpression.operator(),
                        factory.create(leftExpression.right(), operator(), right())
                                .applyPrecedence(factory));
        }
        if (right() instanceof Expression) {
            E rightExpression = (E) right();
            if (compareRight(rightExpression))
                return (N) factory.create(factory.create(left(), operator(),
                                rightExpression.left()).applyPrecedence(factory),
                        rightExpression.operator(), rightExpression.right());
        }
        return (N) this;
    }

    default boolean compareRight(E rightExpression) {
        return operator().isPrecedentThan(rightExpression.operator());
    }

    default boolean compareLeft(E leftExpression) {
        return operator().isPrecedentThan(leftExpression.operator());
    }
}
