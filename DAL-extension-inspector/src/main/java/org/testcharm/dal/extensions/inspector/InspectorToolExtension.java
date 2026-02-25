package org.testcharm.dal.extensions.inspector;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.Extension;

public class InspectorToolExtension implements Extension {
    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder()
                .registerMetaProperty("watch", metaData -> {
                    Data<?> data = metaData.data();
                    Inspector.watch(dal, metaData.inputNode().inspect(), data);
                    return data.value();
                });
    }
}
