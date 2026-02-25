package org.testcharm.dal.runtime.checker;

import org.testcharm.dal.runtime.Data;

import java.util.Optional;

public interface CheckerFactory {
    Optional<Checker> create(Data<?> expected, Data<?> actual);
}
