package org.testcharm.pf;

public interface Page<E extends Element<E, ?, ?>> extends Panel<E> {
    Indicator getIndicator();

    default Indicator indicatorOf(Object key, Object... params) {
        return Indicator.indicator(this, key, params);
    }
}
