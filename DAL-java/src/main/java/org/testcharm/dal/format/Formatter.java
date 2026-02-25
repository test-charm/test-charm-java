package org.testcharm.dal.format;

import org.testcharm.dal.runtime.IllegalTypeException;

public interface Formatter<T, R> {

    R convert(T input);

    boolean isValidType(Object input);

    default boolean isValid(T value) {
        try {
            return isValidValue(transform(value));
        } catch (IllegalTypeException ignore) {
            return false;
        }
    }

    default boolean isValidValue(R value) {
        return true;
    }

    default String getFormatterName() {
        return getClass().getSimpleName().replaceFirst("^Formatter", "");
    }

    default R transform(T o) {
        if (isValidType(o))
            return convert(o);
        throw new IllegalTypeException();
    }
}
