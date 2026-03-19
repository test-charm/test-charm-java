package org.testcharm.pf;

import org.testcharm.dal.DAL;
import org.testcharm.jfactory.JFactory;

import java.util.HashMap;
import java.util.Map;

public class AbstractPageFlow implements PageFlow {
    private static AbstractPageFlow instance;
    private final DAL dal;
    private final JFactory jFactory;
    private final Map<String, Object> objects = new HashMap<>();

    AbstractPageFlow(Builder<?, ?> builder) {
        dal = builder.dal;
        jFactory = builder.jFactory;
    }

    @Override
    public DAL dal() {
        return dal;
    }

    @Override
    public JFactory jFactory() {
        return jFactory;
    }

    @Override
    public Map<String, Object> objects() {
        return objects;
    }

    abstract static class Builder<B extends Builder<B, P>, P extends PageFlow> {
        protected DAL dal = DAL.dal("PageFlow");
        protected JFactory jFactory = new JFactory();

        @SuppressWarnings("unchecked")
        public B dal(DAL dal) {
            this.dal = dal;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B jFactory(JFactory jFactory) {
            this.jFactory = jFactory;
            return (B) this;
        }

        public abstract P build();
    }
}
