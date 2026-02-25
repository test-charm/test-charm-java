package org.testcharm.util.function;

import java.util.function.Predicate;

public class When {
    public static <CONDITION> IfFactory<CONDITION> when(Predicate<CONDITION> predicate) {
        return predicate::test;
    }

    public static If when(boolean condition) {
        return () -> condition;
    }
}
