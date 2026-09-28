package org.testcharm.pf;

public abstract class AbstractPage<E extends Element<E, ?, ?>> extends AbstractPanel<E> implements Page<E> {
    protected final Indicator indicator;

    public AbstractPage(E element, Indicator indicator) {
        super(element);
        this.indicator = indicator;
    }

    @Override
    public Indicator getIndicator() {
        return indicator;
    }
}
