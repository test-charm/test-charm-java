package org.testcharm.dal.extensions.basic.zip;

import org.testcharm.dal.DAL;
import org.testcharm.dal.extensions.basic.zip.util.ZipBinary;
import org.testcharm.dal.extensions.basic.zip.util.ZipBinaryDumper;
import org.testcharm.dal.extensions.basic.zip.util.ZipNodeDumper;
import org.testcharm.dal.extensions.basic.zip.util.ZipNodeJavaClassPropertyAccessor;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.testcharm.dal.runtime.TextAttribute;
import org.testcharm.dal.runtime.TextFormatter;

import static org.testcharm.dal.extensions.basic.binary.BinaryExtension.readAll;
import static org.testcharm.dal.extensions.basic.file.util.FileGroup.register;
import static org.testcharm.dal.extensions.basic.zip.Methods.gzip;

@SuppressWarnings("unused")
public class ZipExtension implements Extension {
    private static final ZipBinaryDumper ZIP_BINARY_DUMPER = new ZipBinaryDumper();
    private static final ZipNodeDumper ZIP_NODE_DUMPER = new ZipNodeDumper();

    @Override
    public void extend(DAL dal) {
        RuntimeContextBuilder runtimeContextBuilder = dal.getRuntimeContextBuilder();
        runtimeContextBuilder.registerStaticMethodExtension(Methods.class)
                .registerImplicitData(ZipBinary.ZipNode.class, ZipBinary.ZipNode::open)
                .registerPropertyAccessor(ZipBinary.ZipNode.class, new ZipNodeJavaClassPropertyAccessor())
                .registerDumper(ZipBinary.class, data -> ZIP_BINARY_DUMPER)
                .registerDumper(ZipBinary.ZipNode.class, data -> ZIP_NODE_DUMPER)
                .registerTextFormatter("GZIP", new TextFormatter<Object, byte[]>() {
                    @Override
                    protected byte[] format(Object content, TextAttribute attribute, RuntimeContextBuilder.DALRuntimeContext context) {
                        if (content instanceof byte[]) {
                            return gzip((byte[]) content);
                        } else if (content instanceof String)
                            return gzip((String) content);
                        throw new IllegalArgumentException("Unsupported type for GZIP: " + content.getClass());
                    }
                });

        register("zip", inputStream -> new ZipBinary(readAll(inputStream)));
        register("ZIP", inputStream -> new ZipBinary(readAll(inputStream)));
    }
}
