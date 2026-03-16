package org.testcharm.dal.runtime;

public class StaticAdaptiveList<T> implements AdaptiveList<T> {
    private final DALCollection<T> list;

    public StaticAdaptiveList(DALCollection<T> list) {
        this.list = list;
    }

    @Override
    public DALCollection<T> list() {
        return list;
    }
}
