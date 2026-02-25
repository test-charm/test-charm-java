package org.testcharm.dal.type;

import org.testcharm.dal.runtime.Data;

public interface Schema {
    default void verify(Data<?> data) {
    }
}
