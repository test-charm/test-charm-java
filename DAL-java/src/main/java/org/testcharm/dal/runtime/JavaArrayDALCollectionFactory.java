package org.testcharm.dal.runtime;

import org.testcharm.util.CollectionHelper;

import java.util.stream.Collectors;

public class JavaArrayDALCollectionFactory extends CollectionDALCollection<Object> {
    public JavaArrayDALCollectionFactory(Object array) {
        super(CollectionHelper.toStream(array).collect(Collectors.toList()));
    }
}
