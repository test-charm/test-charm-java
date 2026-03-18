package org.testcharm.pf;

import org.testcharm.dal.DAL;
import org.testcharm.jfactory.JFactory;

public class PageFlow {
    private static DAL dal;
    private static JFactory jFactory;

    static DAL dal() {
        if (dal == null)
            dal = DAL.dal("PageFlow");
        return dal;
    }

    static JFactory jFactory() {
        if (jFactory == null)
            jFactory = new JFactory();
        return jFactory;
    }

    public static void setDAL(DAL dal) {
        PageFlow.dal = dal;
    }

    public static void setDal(JFactory jFactory) {
        PageFlow.jFactory = jFactory;
    }
}
