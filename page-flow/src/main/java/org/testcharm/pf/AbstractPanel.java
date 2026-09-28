package org.testcharm.pf;

//TODO need test
public class AbstractPanel<E extends Element<E, ?, ?>> implements Panel<E> {
    private final E element;

    public AbstractPanel(E element) {
        this.element = element;
    }

    @Override
    public E element() {
        return element;
    }

    @Override
    public String toString() {
        return getClass() + "\n" + element().getDom();
    }
}
