package org.testcharm.util.extensions;

import org.testcharm.util.Converter;
import org.testcharm.util.ConverterExtension;

import java.util.function.Consumer;

public class TestConverterExtension implements ConverterExtension {
    public static Consumer<Converter> extender = c -> {
    };

    @Override
    public void extend(Converter converter) {
        extender.accept(converter);
    }
}
