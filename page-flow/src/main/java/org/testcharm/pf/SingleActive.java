package org.testcharm.pf;

import java.util.List;
import java.util.Objects;

public class SingleActive {
    private final PageFlow pageFlow;
    private List<Object> id;
    private Page<?> active;

    public SingleActive(PageFlow pageFlow) {
        this.pageFlow = pageFlow;
    }

    public Page<?> launch(Indicator indicator, Launcher<?> launcher) {
        List<Object> id = indicator.getId();
        if (Objects.equals(this.id, id))
            return active;
        active = launcher.launch(indicator, pageFlow.pageFactory());
        this.id = id;
        return active;
    }
}
