package org.testcharm.dal.extensions.inspector;

@Deprecated
public interface Target<P> {
    void navigateTo();

    P create();

    default boolean matches(P current) {
        return false;
    }
}
