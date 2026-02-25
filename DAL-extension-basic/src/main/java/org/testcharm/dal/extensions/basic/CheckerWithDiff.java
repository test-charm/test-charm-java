package org.testcharm.dal.extensions.basic;

import org.testcharm.dal.runtime.checker.Checker;
import org.testcharm.dal.runtime.checker.CheckingContext;

public abstract class CheckerWithDiff implements Checker, CheckerType {
    @Override
    public String message(CheckingContext checkingContext) {
        StringBuilder result = new StringBuilder(checkingContext.verificationMessage(getType(), ""));
        String detail = new Diff("Detail:\nExpect:", expectedDetail(checkingContext), actualDetail(checkingContext)).detail();
        if (!detail.isEmpty())
            result.append("\n\n").append(detail);
        return result.toString();
    }

    protected abstract String actualDetail(CheckingContext checkingContext);

    protected abstract String expectedDetail(CheckingContext checkingContext);
}
