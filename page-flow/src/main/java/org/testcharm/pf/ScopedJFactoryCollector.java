package org.testcharm.pf;

import org.testcharm.dal.runtime.Scoped;
import org.testcharm.jfactory.JFactory;
import org.testcharm.jfactory.JFactoryCollector;

public abstract class ScopedJFactoryCollector extends JFactoryCollector implements Scoped {
    protected ScopedJFactoryCollector(JFactory jFactory, Class<?> defaultType) {
        super(jFactory, defaultType);
    }

    protected ScopedJFactoryCollector(JFactory jFactory, String... traitsSpec) {
        super(jFactory, traitsSpec);
    }
}
