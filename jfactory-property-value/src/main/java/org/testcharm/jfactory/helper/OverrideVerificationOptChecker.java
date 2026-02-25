package org.testcharm.jfactory.helper;

import org.testcharm.dal.runtime.checker.Checker;
import org.testcharm.dal.runtime.checker.CheckingContext;

import java.util.function.BiConsumer;

class OverrideVerificationOptChecker<A, E> implements Checker {
    private final BiConsumer<A, E> runnable;

    public OverrideVerificationOptChecker(BiConsumer<A, E> runnable) {
        this.runnable = runnable;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean failed(CheckingContext checkingContext) {
        runnable.accept((A) checkingContext.getOriginalActual().value(),
                (E) checkingContext.getOriginalExpected().value());
        return false;
    }
}
