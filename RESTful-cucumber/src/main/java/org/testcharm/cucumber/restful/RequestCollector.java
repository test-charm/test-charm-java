package org.testcharm.cucumber.restful;

import org.testcharm.jfactory.JFactory;
import org.testcharm.jfactory.JFactoryCollector;
import org.testcharm.util.Collector;

public class RequestCollector extends JFactoryCollector {
    private final Collector headerCollector;

    protected RequestCollector(JFactory jFactory) {
        super(jFactory, Object.class);
        headerCollector = jFactory.collector();
    }

    public Collector headerCollector() {
        return headerCollector;
    }
}
