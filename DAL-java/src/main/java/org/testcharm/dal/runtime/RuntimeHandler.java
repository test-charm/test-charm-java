package org.testcharm.dal.runtime;

public interface RuntimeHandler<R extends RuntimeData<?>> {
    default Data<?> handleData(R r) {
        return r.runtimeContext().data(handle(r));
    }

    Object handle(R r);
}
