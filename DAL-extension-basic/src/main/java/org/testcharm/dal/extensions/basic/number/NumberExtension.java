package org.testcharm.dal.extensions.basic.number;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Extension;

@SuppressWarnings("unused")
public class NumberExtension implements Extension {

    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder().registerStaticMethodExtension(Methods.class);
    }
}
