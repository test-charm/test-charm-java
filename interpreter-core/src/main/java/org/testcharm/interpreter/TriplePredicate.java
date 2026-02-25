package org.testcharm.interpreter;

@FunctionalInterface
public interface TriplePredicate<T, U, V> {
    boolean test(T t, U u, V v);
}
