package org.testcharm.util;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Throwable;
}
