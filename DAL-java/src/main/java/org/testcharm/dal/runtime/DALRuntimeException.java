package org.testcharm.dal.runtime;

import static org.testcharm.dal.runtime.DALException.buildMessage;

public class DALRuntimeException extends RuntimeException {
    public DALRuntimeException(String message) {
        this(message, null);
    }

    public DALRuntimeException() {
        this(null, null);
    }

    public DALRuntimeException(Throwable cause) {
        this(null, cause);
    }

    public DALRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return buildMessage(this, super.getMessage());
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
