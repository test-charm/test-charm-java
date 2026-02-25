package org.testcharm.util;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Throwable;
}
