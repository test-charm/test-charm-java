package org.testcharm.dal.extensions.jdbc;

import io.cucumber.core.backend.ObjectFactory;
import org.testcharm.jfactory.JFactory;
import org.testcharm.jfactory.Spec;
import org.testcharm.jfactory.cucumber.JData;
import org.testcharm.jfactory.repo.JPADataRepository;
import org.testcharm.util.Classes;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PicoFactory implements ObjectFactory {
    private final EntityManager entityManager = Persistence.createEntityManagerFactory("jfactory-repo").createEntityManager();
    private final io.cucumber.picocontainer.PicoFactory delegate = new io.cucumber.picocontainer.PicoFactory();
    private final Object jData = jData();
    public static JFactory jFactory;

    @Override
    public void start() {
        delegate.start();
    }

    @Override
    public void stop() {
        delegate.stop();
    }

    @Override
    public boolean addClass(Class<?> glueClass) {
        return delegate.addClass(glueClass);
    }

    @Override
    public <T> T getInstance(Class<T> glueClass) {
        if (JData.class.equals(glueClass))
            return (T) jData;
        return delegate.getInstance(glueClass);
    }

    private JData jData() {
        jFactory = new JFactory(new JPADataRepository(entityManager));
        Classes.assignableTypesOf(Spec.class, "org.testcharm.dal.extensions.jdbc").forEach(jFactory::register);
        return new JData(jFactory);
    }
}
