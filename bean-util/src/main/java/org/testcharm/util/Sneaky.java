package org.testcharm.util;

import java.lang.reflect.InvocationTargetException;
import java.util.function.*;

public class Sneaky {
    public static <T> T get(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            return sneakyThrow(e);
        }
    }

    public static <R> Supplier<R> sneakyGet(ThrowingSupplier<R> fun) {
        return () -> {
            try {
                return fun.get();
            } catch (Throwable e) {
                return sneakyThrow(e);
            }
        };
    }

    public static <A, R> Function<A, R> sneakyGet(ThrowingFunction<A, R> fun) {
        return a -> {
            try {
                return fun.apply(a);
            } catch (Throwable e) {
                return sneakyThrow(e);
            }
        };
    }

    public static <A1, A2, R> BiFunction<A1, A2, R> sneakyGet(ThrowingBiFunction<A1, A2, R> fun) {
        return (a1, a2) -> {
            try {
                return fun.apply(a1, a2);
            } catch (Throwable e) {
                return sneakyThrow(e);
            }
        };
    }

    public static void run(ThrowingRunnable runnable) {
        get(() -> {
            runnable.run();
            return null;
        });
    }

    public static Runnable sneakyRun(ThrowingRunnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Throwable e) {
                sneakyThrow(e);
            }
        };
    }

    public static <A> Consumer<A> sneakyRun(ThrowingConsumer<A> consumer) {
        return a -> {
            try {
                consumer.accept(a);
            } catch (Throwable e) {
                sneakyThrow(e);
            }
        };
    }

    public static <A1, A2> BiConsumer<A1, A2> sneakyRun(ThrowingBiConsumer<A1, A2> consumer) {
        return (a1, a2) -> {
            try {
                consumer.accept(a1, a2);
            } catch (Throwable e) {
                sneakyThrow(e);
            }
        };
    }

    public static <T> T execute(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (InvocationTargetException e) {
            return sneakyThrow(e.getTargetException());
        } catch (Throwable e) {
            return sneakyThrow(e);
        }
    }

    public static void executeVoid(ThrowingRunnable runnable) {
        execute(() -> {
            runnable.run();
            return null;
        });
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable, T> T sneakyThrow(Throwable throwable) throws E {
        throw (E) throwable;
    }

    @SuppressWarnings("unchecked")
    public static <T, R> R cast(T input) {
        return (R) input;
    }

    @FunctionalInterface
    public interface ThrowingConsumer<A> {
        void accept(A a) throws Throwable;
    }

    @FunctionalInterface
    public interface ThrowingBiConsumer<A1, A2> {
        void accept(A1 a1, A2 a2) throws Throwable;
    }

    @FunctionalInterface
    public interface ThrowingFunction<A, R> {
        R apply(A a) throws Throwable;
    }

    @FunctionalInterface
    public interface ThrowingBiFunction<A1, A2, R> {
        R apply(A1 a1, A2 a2) throws Throwable;
    }
}
