package org.testcharm.extensions.util;

import org.testcharm.util.AccessorFilter;
import org.testcharm.util.AccessorFilterExtension;

public class FilterExtension implements AccessorFilterExtension {
    @Override
    public void extend(AccessorFilter accessorFilter) {
        accessorFilter.exclude(propertyAccessor ->
                propertyAccessor.getName().equals("excludeProperty")
                        && propertyAccessor.getGenericType().equals(int.class));
    }
}
