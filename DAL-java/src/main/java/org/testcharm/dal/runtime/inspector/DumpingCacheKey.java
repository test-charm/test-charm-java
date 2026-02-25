package org.testcharm.dal.runtime.inspector;

import org.testcharm.dal.runtime.Data;

import java.util.Objects;

class DumpingCacheKey {
    private final Data<?> data;

    public DumpingCacheKey(Data<?> data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data.value());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DumpingCacheKey)) return false;
        return ((DumpingCacheKey) obj).data.value() == data.value();
    }
}
