package org.testcharm.dal.runtime.checker;

import org.testcharm.dal.runtime.DALRuntimeException;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder.DALRuntimeContext;
import org.testcharm.util.ConvertException;

import java.util.function.Function;

import static org.testcharm.dal.runtime.ExpressionException.opt1;
import static org.testcharm.dal.runtime.ExpressionException.opt2;

public interface Checker {
    Checker MATCH_NULL_CHECKER = new Checker() {
        @Override
        public String message(CheckingContext checkingContext) {
            return checkingContext.messageMatch();
        }
    };
    Checker EQUALS_CHECKER = new EqualsChecker();
    Checker MATCHES_CHECKER = new MatchesChecker();

    static Checker forceFailed(Function<CheckingContext, String> message) {
        return new Checker() {
            @Override
            public boolean failed(CheckingContext checkingContext) {
                return true;
            }

            @Override
            public String message(CheckingContext checkingContext) {
                return message.apply(checkingContext);
            }
        };
    }

    default boolean failed(CheckingContext checkingContext) {
        return checkingContext.objectNotEquals();
    }

    default String message(CheckingContext checkingContext) {
        return "Failed";
    }

    default Data<?> transformActual(Data<?> actual, Data<?> expected, DALRuntimeContext context) {
        return actual;
    }

    default Data<?> transformActualAndCheck(Data<?> actual, Data<?> expected, DALRuntimeContext context) {
        try {
            return transformActual(actual, expected, context);
        } catch (ConvertException e) {
            throw new DALRuntimeException(e.getMessage());
        }
    }

    default Data<?> transformExpected(Data<?> expected, DALRuntimeContext context) {
        return expected;
    }

    default Data<?> verify(CheckingContext checkingContext) {
        return checkingContext.getOriginalActual().map(data -> {
            if (failed(checkingContext))
                throw new AssertionError(message(checkingContext));
            return data;
        });
    }

    default Data<?> verify(Data<?> expected, Data<?> actual, DALRuntimeContext context) {
        return verify(new CheckingContext(expected, actual,
                opt2(() -> transformExpected(expected, context)),
                opt1(() -> transformActualAndCheck(actual, expected, context))));
    }
}
