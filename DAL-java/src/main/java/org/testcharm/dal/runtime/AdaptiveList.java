package org.testcharm.dal.runtime;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

public interface AdaptiveList<T> {
    DALCollection<T> list();

    default List<T> soloList() {
        DALCollection<T> list = list();
        Iterator<IndexedElement<T>> iterator = list.iterator();
        if (iterator.hasNext()) {
            IndexedElement<T> next = iterator.next();
            if (!iterator.hasNext())
                return singletonList(next.value());
        }
        throw new InvalidAdaptiveListException("Expected list can only have one element");
    }

    default T single() {
        return soloList().get(0);
    }

    static <T> AdaptiveList<T> staticList(Collection<T> list) {
        return new StaticAdaptiveList<>(new CollectionDALCollection<>(list));
    }

    static <T> AdaptiveList<T> staticList(Supplier<T> supplier) {
        return new StaticAdaptiveList<>(new InfiniteDALCollection<>(supplier));
    }

    default Stream<T> stream() {
        return list().values();
    }
}
