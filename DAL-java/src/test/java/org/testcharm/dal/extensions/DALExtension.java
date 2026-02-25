package org.testcharm.dal.extensions;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Extension;

public class DALExtension implements Extension {

    public static Extension extensionForTest = dal -> {
    };

    @Override
    public void extend(DAL dal) {
        extensionForTest.extend(dal);
    }
}
