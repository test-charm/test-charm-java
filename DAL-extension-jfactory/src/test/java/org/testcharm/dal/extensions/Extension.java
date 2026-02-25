package org.testcharm.dal.extensions;

import org.testcharm.dal.DAL;
import org.testcharm.jfactory.Collector;

public class Extension implements org.testcharm.dal.runtime.Extension {
    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder()
                .registerMetaProperty(Collector.class, "properties",
                        runtimeData -> runtimeData.data().value().properties())
                .registerMetaProperty(Collector.class, "build",
                        runtimeData -> runtimeData.data().value().build())
        ;
    }
}
