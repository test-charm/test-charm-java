package org.testcharm.dal.runtime;

public interface ErrorHook {
    boolean handle(Data input, String code, Throwable error);
}
