package org.testcharm.pf;

import org.testcharm.dal.DAL;
import org.testcharm.jfactory.JFactory;

import java.util.HashMap;
import java.util.Map;

public class AbstractPageFlow implements PageFlow {
    private final DAL dal;
    private final DAL dalPredicate;
    private final JFactory jFactory;
    private final Map<String, Object> objects = new HashMap<>();
    private final PageFactory pageFactory = new PageFactory(this);

    AbstractPageFlow(Builder<?, ?> builder) {
        dal = builder.dal;
        dalPredicate = builder.dalPredicate;
        jFactory = builder.jFactory;
    }

    @Override
    public DAL dal() {
        return dal;
    }

    @Override
    public DAL dalPredicate() {
        return dalPredicate;
    }

    @Override
    public JFactory jFactory() {
        return jFactory;
    }

    @Override
    public Map<String, Object> objects() {
        return objects;
    }

    @Override
    public PageFactory pageFactory() {
        return pageFactory;
    }

    abstract static class Builder<B extends Builder<B, P>, P extends PageFlow> {
        protected DAL dal = DAL.dal("PageFlow");
        protected DAL dalPredicate = DAL.dal("PageFlow.Predicate");
        protected JFactory jFactory = new JFactory();

        @SuppressWarnings("unchecked")
        public B dal(DAL dal) {
            this.dal = dal;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B dalPredicate(DAL dalPredicate) {
            this.dalPredicate = dalPredicate;
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
