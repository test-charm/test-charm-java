package org.testcharm.dal.extensions.basic.zip;

import org.testcharm.dal.extensions.basic.zip.util.ZipBinary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static org.testcharm.dal.extensions.basic.binary.BinaryExtension.readAllAndClose;
import static org.testcharm.util.Sneaky.get;

public class Methods {
    public static ZipBinary unzip(byte[] data) {
        return new ZipBinary(data);
    }

    public static byte[] gzip(byte[] data) {
        return get(() -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (GZIPOutputStream gzipOut = new GZIPOutputStream(outputStream)) {
                gzipOut.write(data);
            }
            return outputStream.toByteArray();
        });
    }

    public static byte[] ungzip(byte[] data) {
        return get(() -> readAllAndClose(new GZIPInputStream(new ByteArrayInputStream(data))));
    }

    public static byte[] gzip(String data) {
        return gzip(data.getBytes());
    }
}
