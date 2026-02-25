package org.testcharm.dal.extensions.basic.zip.util;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.inspector.Dumper;
import org.testcharm.dal.runtime.inspector.DumpingBuffer;

public class ZipNodeDumper implements Dumper<ZipBinary.ZipNode> {

    @Override
    public void dump(Data<ZipBinary.ZipNode> data, DumpingBuffer context) {
        ZipBinary.ZipNode node = data.value();
        if (node.isDirectory()) {
            DumpingBuffer sub = context.append(node.name()).append("/").indent();
            data.list().wraps().values().forEach(subFile -> sub.newLine().dumpValue(subFile));
        } else if (node.name().toLowerCase().endsWith(".zip"))
            context.append(node.name()).indent().dumpValue(data.property("unzip"));
        else
            context.append(String.format("%s %6s %s", node.lastModifiedTime(), node.getSize(), node.name()));
    }
}
