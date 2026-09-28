package org.testcharm.pf;

import org.testcharm.util.Sneaky;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiToggle {
    private final PageFlow pageFlow;

    private final Map<List<Object>, Page<?>> launched = new HashMap<>();

    public MultiToggle(PageFlow pageFlow) {
        this.pageFlow = pageFlow;
    }

    public Page<?> launch(Indicator indicator, Launcher<?> launcher) {
        return launched.computeIfAbsent(indicator.getId(), _ignore ->
                launcher.launch(indicator, Sneaky.cast(pageFlow.pageFactory())));
    }
}
