package org.testcharm.pf;

public interface Target<P> {
    void navigateTo();

    P create();

    default boolean matches(P current) {
        return false;
    }
}
