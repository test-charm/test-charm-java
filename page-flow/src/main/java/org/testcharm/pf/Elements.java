package org.testcharm.pf;

import org.testcharm.dal.extensions.basic.sync.Retryer;
import org.testcharm.dal.runtime.CollectionDALCollection;
import org.testcharm.dal.runtime.DALCollection;
import org.testcharm.dal.runtime.InvalidSoloListException;
import org.testcharm.dal.runtime.SoloList;
import org.testcharm.util.IndentBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.lang.System.identityHashCode;

public interface Elements<E extends Element<E, ?, ?>> extends SoloList<E> {

    @SafeVarargs
    static <T extends Element<T, ?, ?>> Elements<T> concat(Elements<T> elements1, Elements<T>... elementsList) {
        return new GroupElements<>(elements1, elementsList);
    }

    default Elements<E> filter(Predicate<E> predicate) {
        return filter(predicate, "source code");
    }

    default Elements<E> filter(Predicate<E> predicate, String name) {
        return new Elements<E>() {
            @Override
            public int timeout() {
                return Elements.this.timeout();
            }

            @Override
            public DALCollection<E> list() {
                String objectId = Integer.toHexString(identityHashCode(this)).toUpperCase();
                Element.logger.info(String.format("Filtering by %s(@%s)", name, objectId));
                DALCollection<E> list = Elements.this.list();
                DALCollection<E> filtered = list.filter(predicate);
                Element.logger.info(String.format("Filtered from %d to %d elements by %s(@%s)", list.size(),
                        filtered.size(), name, objectId));
                return filtered;
            }

            @Override
            public IndentBuffer locateInfo(IndentBuffer indentBuffer) {
                String objectId = Integer.toHexString(identityHashCode(this)).toUpperCase();
                indentBuffer.append("Filtering by ").append(name).append(String.format("(@%s)", objectId));
                Elements.this.locateInfo(indentBuffer.indent().newLine());
                return indentBuffer;
            }
        };
    }

    default Elements<E> visible() {
        return filter(e -> e.isVisible(), "visible");
    }

    @Override
    default E single() {
        try {
            Element.logger.info(String.format("Locating... (%dms)", timeout()));
            return new Retryer(timeout(), 100).get(SoloList.super::single);
        } catch (InvalidSoloListException ig) {
            IndentBuffer buffer = IndentBuffer.create()
                    .append("Operations can only be performed on a single located element at:");
            locateInfo(buffer.newLine());
            buffer.newLine().append("but found ").append(ig.list().size());
            throw new InvalidSoloListException(buffer.content(), ig.list());
        }
    }

    int timeout();

    IndentBuffer locateInfo(IndentBuffer indentBuffer);

    class GroupElements<T extends Element<T, ?, ?>> implements Elements<T> {
        private final List<Elements<T>> subElements = new ArrayList<>();

        @SafeVarargs
        public GroupElements(Elements<T> elements1, Elements<T>... elementsList) {
            addElements(elements1);
            for (Elements<T> each : elementsList)
                addElements(each);
        }

        private void addElements(Elements<T> sub) {
            if (sub instanceof GroupElements)
                subElements.addAll(((GroupElements<T>) sub).subElements());
            else
                subElements.add(sub);
        }

        List<Elements<T>> subElements() {
            return subElements;
        }

        @Override
        public int timeout() {
            return subElements.stream().mapToInt(Elements::timeout).max().orElse(0);
        }

        @Override
        public IndentBuffer locateInfo(IndentBuffer indentBuffer) {
            String objectId = Integer.toHexString(identityHashCode(this)).toUpperCase();
            indentBuffer.append(String.format("Group(@%s):", objectId));
            IndentBuffer indent = indentBuffer.indent();
            for (Elements<T> other : subElements)
                other.locateInfo(indent.newLine());
            return indentBuffer;
        }

        @Override
        public DALCollection<T> list() {
            String objectId = Integer.toHexString(identityHashCode(this)).toUpperCase();
            Element.logger.info(String.format("Group(@%s) locating...", objectId));
            List<T> list = new ArrayList<T>() {{
                for (Elements<T> other : subElements)
                    addAll(other.list().collect());
            }};
            Element.logger.info(String.format("Group(@%s) found a total of %d elements", objectId, list.size()));
            return new CollectionDALCollection<>(list);
        }
    }
}
