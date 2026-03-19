package org.testcharm.pf;

import org.testcharm.dal.Accessors;

public interface Panel<E extends Element<E, ?, ?>> {
    E element();

    default <O> O perform(String expression) {
        return perform(expression, null);
    }

    default <O> O perform(String expression, Object constants) {
        return Accessors.get(expression).by(element().pageFlow().dal()).constants(constants).from(element());
    }

    default Elements<E> locate(String expression) {
        return locate(expression, null);
    }

    @SuppressWarnings("unchecked")
    default Elements<E> locate(String expression, Object constants) {
        Object elements = Accessors.get(expression).by(element().pageFlow().dal()).constants(constants).from(element());
        if (elements instanceof Elements)
            return (Elements<E>) elements;
        throw new IllegalStateException("Locate should return type Elements, but got: " + elements);
    }
}
