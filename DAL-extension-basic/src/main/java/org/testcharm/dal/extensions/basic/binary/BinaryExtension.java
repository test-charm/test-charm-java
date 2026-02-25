package org.testcharm.dal.extensions.basic.binary;

import org.testcharm.dal.DAL;
import org.testcharm.dal.extensions.basic.binary.util.HexChecker;
import org.testcharm.dal.extensions.basic.binary.util.HexDumper;
import org.testcharm.dal.extensions.basic.binary.util.HexFormatter;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.dal.runtime.JavaArrayDALCollectionFactory;
import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.testcharm.dal.runtime.inspector.Dumper;
import org.testcharm.util.Sneaky;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class BinaryExtension implements Extension {
    private static final HexFormatter HEX_FORMATTER = new HexFormatter();
    private static final Dumper<Object> HEX_DUMPER = new HexDumper<>();

    private static final Class<?>[] BINARY_TYPES = new Class<?>[]{byte[].class, Byte[].class, InputStream.class};

    public static byte[] readAllAndClose(InputStream stream) {
        try {
            return readAll(stream);
        } finally {
            Sneaky.run(stream::close);
        }
    }

    public static byte[] readAll(InputStream stream) {
        return Sneaky.get(() -> {
            try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                int size;
                byte[] data = new byte[1024];
                while ((size = stream.read(data, 0, data.length)) != -1)
                    buffer.write(data, 0, size);
                return buffer.toByteArray();
            }
        });
    }

    @Override
    public void extend(DAL dal) {
        RuntimeContextBuilder contextBuilder = dal.getRuntimeContextBuilder();
        contextBuilder
                .registerStaticMethodExtension(Methods.class)
                .registerDALCollectionFactory(InputStream.class, (stream) ->
                        new JavaArrayDALCollectionFactory(readAllAndClose(stream)))
                .registerImplicitData(InputStream.class, BinaryExtension::readAllAndClose)
                .registerTextFormatter("HEX", HEX_FORMATTER);

        for (Class<?> binaryType : BINARY_TYPES)
            extendBinary(contextBuilder, binaryType);
    }

    @SuppressWarnings("unchecked")
    private void extendBinary(RuntimeContextBuilder contextBuilder, Class<?> type) {
        contextBuilder.registerDumper(type, data -> (Dumper) HEX_DUMPER);
        for (Class<?> binaryType : BINARY_TYPES)
            contextBuilder.checkerSetForEqualing().register(type, binaryType, HexChecker::equals);
        contextBuilder.checkerSetForMatching().register(type, HexChecker::matches);
    }
}
