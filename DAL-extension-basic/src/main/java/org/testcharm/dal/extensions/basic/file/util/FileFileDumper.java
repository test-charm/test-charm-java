package org.testcharm.dal.extensions.basic.file.util;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.inspector.Dumper;
import org.testcharm.dal.runtime.inspector.DumpingBuffer;

import java.io.File;

public class FileFileDumper implements Dumper<File> {

    @Override
    public void dump(Data<File> data, DumpingBuffer buffer) {
        buffer.append("java.io.File").newLine().dumpValue(data);
    }

    @Override
    public void dumpValue(Data<File> data, DumpingBuffer buffer) {
        buffer.append(Util.attribute(data.value().toPath()));
    }
}
