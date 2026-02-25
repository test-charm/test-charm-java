package org.testcharm.dal.extensions;

import org.testcharm.dal.runtime.AutoMappingList;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.JavaClassPropertyAccessor;
import org.testcharm.util.BeanClass;

class AutoMappingListPropertyAccessor extends JavaClassPropertyAccessor<AutoMappingList> {
    public AutoMappingListPropertyAccessor() {
        super(BeanClass.create(AutoMappingList.class));
    }

    @Override
    public Object getValue(Data<AutoMappingList> data, Object property) {
        return data.list().autoMapping(item -> item.property(property));
    }
}
