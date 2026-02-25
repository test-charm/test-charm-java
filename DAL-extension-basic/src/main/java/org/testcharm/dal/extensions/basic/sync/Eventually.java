package org.testcharm.dal.extensions.basic.sync;

import org.testcharm.dal.ast.opt.DALOperator;
import org.testcharm.dal.extensions.basic.TimeUtil;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

public class Eventually {
    private static int defaultWaitingTime = 5000;
    private final Data data;
    private final int interval;
    private final int waitingTime;

    public Eventually(Data data) {
        this(data, 100, defaultWaitingTime);
    }

    public Eventually(Data data, int interval, int waitingTime) {
        this.data = data;
        this.interval = interval;
        this.waitingTime = waitingTime;
    }

    public static void setDefaultWaitingTime(int defaultWaitingTime) {
        Eventually.defaultWaitingTime = defaultWaitingTime;
    }

    public Object verify(DALOperator operator, Data v2, RuntimeContextBuilder.DALRuntimeContext context) {
        return new Retryer(waitingTime, interval).get(() -> context.calculate(data, operator, v2).value());
    }

    public Eventually within(String s) {
        return new Eventually(data, interval, TimeUtil.parseTime(s));
    }

    public Eventually interval(String s) {
        return new Eventually(data, TimeUtil.parseTime(s), waitingTime);
    }
}
