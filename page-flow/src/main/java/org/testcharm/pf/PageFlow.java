package org.testcharm.pf;

import org.testcharm.dal.DAL;

public class PageFlow {
    private static DAL dal;

    static DAL dal() {
        if (dal == null)
            dal = DAL.dal("PageFlow");
        return dal;
    }

    public static void setDAL(DAL dal) {
        PageFlow.dal = dal;
    }
}
