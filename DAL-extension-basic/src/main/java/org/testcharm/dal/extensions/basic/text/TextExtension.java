package org.testcharm.dal.extensions.basic.text;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.testcharm.dal.runtime.RuntimeContextBuilder.DALRuntimeContext;
import org.testcharm.dal.runtime.TextAttribute;
import org.testcharm.dal.runtime.TextFormatter;

import static org.testcharm.dal.extensions.basic.binary.BinaryExtension.readAll;
import static org.testcharm.dal.extensions.basic.file.util.FileGroup.register;
import static org.testcharm.dal.extensions.basic.text.Methods.json;
import static org.testcharm.dal.extensions.basic.text.Methods.yaml;

@SuppressWarnings("unused")
public class TextExtension implements Extension {

    @Override
    public void extend(DAL dal) {
        RuntimeContextBuilder builder = dal.getRuntimeContextBuilder();
        builder.registerStaticMethodExtension(Methods.class);

        builder.registerTextFormatter("JSON", jsonTextFormatter("JSON"));
        builder.registerTextFormatter("json", jsonTextFormatter("json"));
        register("json", inputStream -> json(readAll(inputStream)));
        register("JSON", inputStream -> json(readAll(inputStream)));

        builder.registerTextFormatter("YAML", yamlTextFormatter("YAML"));
        builder.registerTextFormatter("yaml", yamlTextFormatter("yaml"));
        register("yaml", inputStream -> yaml(readAll(inputStream)));
        register("YAML", inputStream -> yaml(readAll(inputStream)));
    }

    private TextFormatter<String, Object> jsonTextFormatter(String label) {
        return new TextFormatter<String, Object>() {
            @Override
            protected Object format(String content, TextAttribute attribute, DALRuntimeContext context) {
                return json(content);
            }

            @Override
            public String description() {
                return "use " + label + " as json object";
            }
        };
    }

    private TextFormatter<String, Object> yamlTextFormatter(String label) {
        return new TextFormatter<String, Object>() {
            @Override
            protected Object format(String content, TextAttribute attribute, DALRuntimeContext context) {
                return yaml(content);
            }

            @Override
            public String description() {
                return "use " + label + " to as yaml object";
            }
        };
    }
}
