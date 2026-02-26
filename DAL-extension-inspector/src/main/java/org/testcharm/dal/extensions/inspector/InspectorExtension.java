package org.testcharm.dal.extensions.inspector;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.Extension;

public class InspectorExtension implements Extension {
    @Override
    public void extend(DAL dal) {
        Inspector.launch();
        Inspector.register(dal);
        dal.getRuntimeContextBuilder()
                .registerErrorHook((input, code, error, c) -> Inspector.inspect(dal, input, code, c.constants().value()))
                .registerMetaProperty("inspect", metaData -> {
                    Data<?> data = metaData.data();
                    Inspector.inspect(dal, data, "{}", metaData.runtimeContext().constants().value());
                    return data.value();
                });
    }
}
