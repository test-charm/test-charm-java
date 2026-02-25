package org.testcharm.jfactory;

public interface Persistable {
    default void save(Object object) {
    }
}
