package org.testcharm.pf;

import org.testcharm.dal.extensions.basic.TimeUtil;

public abstract class AbstractElement<E extends Element<E, RE, PF>, RE, PF extends PageFlow> implements Element<E, RE, PF> {
    private By locator;
    private E parent;
    private int timeout = -1;

    private final RE element;
    private final PF pageFlow;

    protected AbstractElement(PF pageFlow, RE e) {
        element = e;
        this.pageFlow = pageFlow;
    }

    @Override
    public PF pageFlow() {
        return pageFlow;
    }

    @Override
    public RE raw() {
        return element;
    }

    @Override
    public By getLocator() {
        return locator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E setLocator(By locator) {
        this.locator = locator;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E patience(String time) {
        E duplicated = duplicate();
        ((AbstractElement<E, RE, PF>) duplicated).timeout = TimeUtil.parseTime(time);
        return duplicated;
    }

    @SuppressWarnings("unchecked")
    protected E duplicate() {
        E duplicated = newChildren(element);
        ((AbstractElement<E, RE, PF>) duplicated).timeout = timeout;
        ((AbstractElement<E, RE, PF>) duplicated).locator = locator;
        return duplicated;
    }

    @Override
    public int timeout() {
        if (timeout == -1)
            return defaultTimeout();
        return timeout;
    }

    @Override
    public E parent() {
        return parent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E parent(E parent) {
        this.parent = parent;
        return (E) this;
    }

    @Override
    public String toString() {
        return getDom();
    }
}
