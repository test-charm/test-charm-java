package org.testcharm.util;

import java.util.List;
import java.util.stream.Collectors;

import static org.testcharm.util.BeanClass.cast;

public class NullPointerInChainException extends RuntimeException {
    public NullPointerInChainException(List<Object> chain, int level) {
        super(makeMessage(chain, level));
    }

    private static String makeMessage(List<Object> chain, int level) {
        List<String> dotChain = chain.stream().map(NullPointerInChainException::chainToString).collect(Collectors.toList());
        dotChain.set(level, "<" + dotChain.get(level) + ">");
        return "Failed to read value at property chain: " + String.join("", dotChain);
    }

    private static String chainToString(Object o) {
        return cast(o, Integer.class).map(i -> "[" + i + "]").orElseGet(() -> "." + o);
    }
}
