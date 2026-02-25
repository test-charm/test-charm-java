package org.testcharm.dal.extensions.basic.list;

import org.testcharm.dal.runtime.AssertionFailure;

public class NotReadyException extends AssertionFailure {
    public NotReadyException(String message, int positionBegin) {
        super(message, positionBegin);
    }
}
