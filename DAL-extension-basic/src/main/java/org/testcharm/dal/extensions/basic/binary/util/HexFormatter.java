package org.testcharm.dal.extensions.basic.binary.util;

import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.testcharm.dal.runtime.TextAttribute;
import org.testcharm.dal.runtime.TextFormatter;
import org.testcharm.interpreter.CharStream;
import org.testcharm.interpreter.Notation;

import java.io.ByteArrayOutputStream;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;

public class HexFormatter extends TextFormatter<String, byte[]> {

    private static byte[] parseBinary(String hexInText) {
        CharStream charStream = new CharStream(hexInText);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        while (charStream.trimBlackAndComment(asList(Notation.notation("#"))).hasContent()) {
            char c = charStream.popChar();
            if (c == ',')
                continue;
            if (charStream.hasContent())
                stream.write(parseInt(String.format("%c%c", c, charStream.popChar()), 16));
            else
                throw new IllegalArgumentException(String.format("incomplete byte: %c, each byte should has 2 hex numbers", c));
        }
        return stream.toByteArray();
    }

    @Override
    public String description() {
        return "use hex numbers as binary data, like 'FF EF 08...'";
    }

    @Override
    public byte[] format(String content, TextAttribute attribute, RuntimeContextBuilder.DALRuntimeContext context) {
        return parseBinary(content);
    }
}
