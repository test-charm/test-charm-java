package org.testcharm.dal.runtime;

public interface DALCollectionFactory<T, E> {
    default boolean isList(T instance) {
        return true;
    }

    DALCollection<E> create(T instance);
}
