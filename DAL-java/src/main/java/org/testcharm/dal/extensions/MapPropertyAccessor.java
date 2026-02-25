package org.testcharm.dal.extensions;

import org.testcharm.dal.runtime.PropertyAccessor;

import java.util.Map;
import java.util.Set;

public class MapPropertyAccessor implements PropertyAccessor<Map<?, ?>> {
    @Override
    public Object getValue(Map<?, ?> instance, Object property) {
        return instance.get(property);
    }

    @Override
    public Set<?> getPropertyNames(Map<?, ?> instance) {
        return instance.keySet();
    }
}
