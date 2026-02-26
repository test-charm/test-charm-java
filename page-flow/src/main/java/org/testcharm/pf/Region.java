package org.testcharm.pf;

import org.testcharm.dal.Accessors;

public interface Region<T extends Element<T, ?>> {
    T element();

    default <O> O perform(String expression) {
        return Accessors.get(expression).by(PageFlow.dal()).from(element());
    }

    default Elements<T> locate(String expression) {
        return Accessors.get(expression).by(PageFlow.dal()).from(element());
    }

    default Elements<T> locate(String expression, Object constants) {
        return Accessors.get(expression).by(PageFlow.dal()).constants(constants).from(element());
    }
}
