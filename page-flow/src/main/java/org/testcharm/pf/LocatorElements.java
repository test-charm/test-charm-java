package org.testcharm.pf;

import org.testcharm.dal.runtime.CollectionDALCollection;
import org.testcharm.dal.runtime.DALCollection;
import org.testcharm.util.IndentBuffer;
import org.testcharm.util.Sneaky;

import java.util.List;
import java.util.stream.Collectors;

public class LocatorElements<E extends Element<E, ?, ?>> implements Elements<E> {
    private final E element;
    private final By locator;

    public LocatorElements(By locator, E element) {
        this.locator = locator;
        this.element = element;
    }

    @Override
    public DALCollection<E> list() {
        Element.logger.info("Selector: " + locateInfo(IndentBuffer.create()));
        List<?> elements = element.findElements(locator);
        Element.logger.info(String.format("Found %d elements", elements.size()));
        return new CollectionDALCollection<>(elements.stream().map(element1 -> {
            E child = element.newChildren(Sneaky.cast(element1));
            child.parent(element);
            child.setLocator(locator);
            return child;
        }).collect(Collectors.toList()));
    }

    @Override
    public int timeout() {
        return element.timeout();
    }

    @Override
    public IndentBuffer locateInfo(IndentBuffer indentBuffer) {
        return indentBuffer.appendAll(" / ", element.locators()).append(" => ").append(locator);
    }
}
