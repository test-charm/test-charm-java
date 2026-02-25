package org.testcharm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AccessorFilter {
    private final List<Predicate<PropertyAccessor<?>>> filters = new ArrayList<>();

    public AccessorFilter exclude(Predicate<PropertyAccessor<?>> predicate) {
        filters.add(predicate.negate());
        return this;
    }

    public boolean test(PropertyAccessor<?> accessor) {
        return filters.stream().allMatch(f -> f.test(accessor));
    }

    public AccessorFilter extend() {
        Classes.subTypesOf(AccessorFilterExtension.class, "org.testcharm.util.extensions")
                .forEach(c -> Classes.newInstance(c).extend(this));
        Classes.subTypesOf(AccessorFilterExtension.class, "org.testcharm.extensions.util")
                .forEach(c -> Classes.newInstance(c).extend(this));
        return this;
    }
}
