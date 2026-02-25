package org.testcharm.dal.runtime;

import org.testcharm.util.BeanClass;
import org.testcharm.util.NoSuchAccessorException;

import java.util.Set;

import static java.lang.String.format;

public class JavaClassPropertyAccessor<T> implements PropertyAccessor<T> {
    private final BeanClass<T> beanClass;

    public JavaClassPropertyAccessor(BeanClass<T> type) {
        beanClass = type;
    }

    @Override
    public Object getValue(T instance, Object property) {
        try {
            return beanClass.getPropertyValue(instance, (String) property);
        } catch (NoSuchAccessorException ignore) {
            throw new InvalidPropertyException(format("Method or property `%s` does not exist in `%s`", property,
                    instance.getClass().getName()));
        }
    }

    @Override
    public Set<?> getPropertyNames(T instance) {
        return beanClass.getPropertyReaders().keySet();
    }
}
