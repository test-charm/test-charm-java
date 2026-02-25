package org.testcharm.dal.runtime.inspector;

import org.testcharm.dal.runtime.Data;

public class StringDumper extends ValueDumper<CharSequence> {
    @Override
    protected void inspectValue(Data<CharSequence> data, DumpingBuffer dumpingBuffer) {
        dumpingBuffer.append("<").append(data.value().toString().replace("\\", "\\\\").replace("\n", "\\n")
                .replace("\r", "\\r").replace("\t", "\\t").replace("\b", "\\b")).append(">");
    }
}
