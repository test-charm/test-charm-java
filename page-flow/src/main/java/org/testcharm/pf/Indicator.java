package org.testcharm.pf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Indicator {
    private final Object key;
    private final Page<?> parent;

    public Indicator(Page<?> parent, Object key) {
        this.parent = parent;
        this.key = key;
    }

    public Object key() {
        return key;
    }

    @SuppressWarnings("unchecked")
    public <E> E item(int index) {
        return (E) ((Object[]) key)[index];
    }

    public Page<?> parent() {
        return parent;
    }

    public List<Object> getId() {
        if (parent != null)
            return new ArrayList<Object>() {{
                addAll(parent.getIndicator().getId());
                add(key);
            }};
        return Collections.singletonList(key);
    }

    public static Indicator indicator(Page<?> parent, Object key, Object... params) {
        if (params.length > 0) {
            Object[] objects = new Object[1 + params.length];
            objects[0] = key;
            System.arraycopy(params, 0, objects, 1, params.length);
            key = objects;
        }
        return new Indicator(parent, key);
    }

    public static Indicator root(Object key, Object... params) {
        return indicator(null, key, params);
    }
}
