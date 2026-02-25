package org.testcharm.dal.runtime.checker;

import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

import java.math.BigDecimal;

public class MatchesChecker implements Checker {

    @Override
    public Data<?> transformActual(Data<?> actual, Data<?> expected, RuntimeContextBuilder.DALRuntimeContext context) {
        return actual.convert(expected.value().getClass());
    }

    @Override
    public String message(CheckingContext checkingContext) {
        return checkingContext.messageMatch();
    }

    @Override
    public boolean failed(CheckingContext checkingContext) {
        return checkingContext.getExpected().cast(BigDecimal.class).flatMap(number1 ->
                        checkingContext.getActual().cast(BigDecimal.class).map(number2 ->
                                number1.compareTo(number2) != 0))
                .orElseGet(() -> Checker.super.failed(checkingContext));
    }
}
