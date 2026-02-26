package org.testcharm.dal.runtime;

import java.util.List;

public class StaticAdaptiveList<T> implements AdaptiveList<T> {
    private final DALCollection<T> list;

    public StaticAdaptiveList(DALCollection<T> list) {
        this.list = list;
    }

    @Override
    public DALCollection<T> list() {
        return list;
    }

    @Override
    public List<T> soloList() {
        if (list.size() != 1)
            throw new InvalidAdaptiveListException("Expected list can only have one element");
        return list.collect();
    }
}
