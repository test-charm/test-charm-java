package org.testcharm.pf;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public interface Launcher<E extends Element<E, ?, ?>> {
    default void open(Indicator indicator) {
    }

    default Page<?> launch(Indicator indicator, PageFactory pageFactory) {
        open(indicator);
        E element = element();
        return pageFactory.create(element, indicator).orElseGet(() -> construct(element, indicator));
    }

    Page<?> construct(E element, Indicator indicator);

    E element();

    static <E extends Element<E, ?, ?>> Launcher<E> launcher(Runnable trigger,
                                                             Supplier<E> elementSupplier,
                                                             BiFunction<E, Indicator, Page<?>> constructor) {
        return new Launcher<E>() {
            @Override
            public void open(Indicator indicator) {
                trigger.run();
            }

            @Override
            public E element() {
                return elementSupplier.get();
            }

            @Override
            public Page<?> construct(E element, Indicator indicator) {
                return constructor.apply(element, indicator);
            }
        };
    }
}
