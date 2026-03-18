package org.testcharm.pf;

import org.testcharm.dal.DAL;
import org.testcharm.jfactory.JFactory;

import java.util.Map;

public interface PageFlow {
    DAL dal();

    JFactory jFactory();

    Map<String, Object> objects();
}
