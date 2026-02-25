package org.testcharm.extensions.dal;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.dal.runtime.inspector.Dumper;
import org.testcharm.dal.runtime.inspector.DumperFactory;
import org.testcharm.dal.runtime.inspector.KeyValueDumper;
import org.testcharm.pf.Element;
import com.github.valfirst.slf4jtest.LoggingEvent;

import java.util.Set;
import java.util.TreeSet;

public class LogExtension implements Extension {
    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder().registerDumper(LoggingEvent.class, loggingEventData -> (data, dumpingBuffer) ->
                dumpingBuffer.append(data.value().toString()));

        dal.getRuntimeContextBuilder().registerDumper(Element.class, new DumperFactory<Element>() {
            @Override
            public Dumper<Element> apply(Data<Element> elementData) {
                return new KeyValueDumper<Element>() {
                    @Override
                    protected Set<?> getFieldNames(Data<Element> data) {
                        return new TreeSet<>(super.getFieldNames(data));
                    }
                };
            }
        });
    }
}
