package org.testcharm.pf;

import org.testcharm.dal.extensions.basic.TimeUtil;

public abstract class AbstractElement<T extends Element<T, E, P>, E, P extends PageFlow> implements Element<T, E, P> {
    private By locator;
    private T parent;
    private int timeout = -1;

    private final E element;
    private final P pageFlow;

    protected AbstractElement(P pageFlow, E e) {
        element = e;
        this.pageFlow = pageFlow;
    }

    @Override
    public P pageFlow() {
        return pageFlow;
    }

    @Override
    public E raw() {
        return element;
    }

    @Override
    public By getLocator() {
        return locator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T setLocator(By locator) {
        this.locator = locator;
        return (T) this;
    }

    @Override
    public T patience(String time) {
        timeout = TimeUtil.parseTime(time);
        return (T) this;
    }

    @Override
    public int timeout() {
        if (timeout == -1)
            return defaultTimeout();
        return timeout;
    }

    @Override
    public T parent() {
        return parent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T parent(T parent) {
        this.parent = parent;
        return (T) this;
    }

    @Override
    public String toString() {
        return getDom();
    }
}
