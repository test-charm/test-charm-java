package org.testcharm.dal.extensions.basic.url;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Extension;

@SuppressWarnings("unused")
public class UrlExtension implements Extension {

    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder().registerStaticMethodExtension(Methods.class);
    }
}
