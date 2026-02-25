package org.testcharm.dal.runtime;

import org.testcharm.dal.DAL;
import org.testcharm.util.BeanClass;

public interface Extension {
    void extend(DAL dal);

    default int order() {
        return BeanClass.createFrom(this).annotation(Order.class).map(Order::value).orElse(Integer.MAX_VALUE);
    }
}
