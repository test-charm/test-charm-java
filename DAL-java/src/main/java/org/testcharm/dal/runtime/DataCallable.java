package org.testcharm.dal.runtime;

import java.util.function.Function;

public interface DataCallable<A, T> extends Function<A, Data<T>> {
}
