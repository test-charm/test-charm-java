package org.testcharm.util;

public class NoSuchPropertyException extends IllegalArgumentException {
    public NoSuchPropertyException(String property) {
        super("No available property: " + property);
    }
}
