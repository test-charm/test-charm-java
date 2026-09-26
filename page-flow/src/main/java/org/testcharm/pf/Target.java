package org.testcharm.pf;

@Deprecated
public interface Target<P> {
    void navigateTo();

    P create();

    default boolean matches(P current) {
        return false;
    }
}
