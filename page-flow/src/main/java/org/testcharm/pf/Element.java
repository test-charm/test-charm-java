package org.testcharm.pf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcharm.dal.Accessors;
import org.testcharm.dal.Evaluator;
import org.testcharm.util.BeanClass;
import org.testcharm.util.Collector;

import java.util.ArrayList;
import java.util.List;

import static org.testcharm.dal.Assertions.expect;

public interface Element<E extends Element<E, RE, PF>, RE, PF extends PageFlow> {
    Logger logger = LoggerFactory.getLogger(Element.class);

    @SuppressWarnings("unchecked")
    default E newChildren(RE element) {
        return (E) BeanClass.create(getClass()).newInstance(pageFlow(), element);
    }

    List<RE> findElements(By by);

    default int defaultTimeout() {
        return 8888;
    }

    int timeout();

    E patience(String time);

    @SuppressWarnings("unchecked")
    default List<By> locators() {
        return new ArrayList<By>() {{
            for (E p = (E) Element.this; p != null; p = p.parent())
                if (p.getLocator() != null)
                    add(0, p.getLocator());
        }};
    }

    String tag();

    String text();

    E click();

    E typeIn(String value);

    E clear();

    default E fillIn(Object value) {
        return clear().typeIn(String.valueOf(value));
    }

    default Collector fillIn() {
        return new ScopedJFactoryCollector(pageFlow().jFactory(), Object.class) {
            @Override
            public void onExit() {
                fillIn(build());
            }

            @Override
            public void setValue(Object value) {
                fillIn(value);
            }
        };
    }

    default boolean isInput() {
        return false;
    }

    By getLocator();

    E setLocator(By locator);

    E parent();

    E parent(E parent);

    default Object value() {
        throw new UnsupportedOperationException("Not support operation");
    }

    byte[] screenshot();

    @SuppressWarnings("unchecked")
    default Elements<E> find(By locator) {
        return new LocatorElements<>(locator, (E) this);
    }

    default Elements<E> css(String css) {
        return find(By.css(css));
    }

    default Elements<E> caption(String text) {
        return find(By.caption(text));
    }

    default Elements<E> xpath(String xpath) {
        return find(By.xpath(xpath));
    }

    default Elements<E> placeholder(String placeholder) {
        return find(By.placeholder(placeholder));
    }

    String getDom();

    PF pageFlow();

    RE raw();

    boolean isEnabled();

    default <O> O perform(String expression) {
        return perform(expression, null);
    }

    default <O> O perform(String expression, Object constants) {
        return Evaluator.evaluate(expression).by(pageFlow().dal()).constants(constants).on(this);
    }

    default <O> O performAll(String expressions) {
        return performAll(expressions, null);
    }

    default <O> O performAll(String expressions, Object constants) {
        return Evaluator.evaluateAll(expressions).by(pageFlow().dal()).constants(constants).on(this);
    }

    default Elements<E> locate(String expression) {
        return locate(expression, null);
    }

    @SuppressWarnings("unchecked")
    default Elements<E> locate(String expression, Object constants) {
        Object elements = Accessors.get(expression).by(pageFlow().dal()).constants(constants).from(this);
        if (elements instanceof Elements)
            return (Elements<E>) elements;
        throw new IllegalStateException("Locate should return type Elements, but got: " + elements);
    }

    default void should(String expression) {
        should(expression, null);
    }

    default void should(String expression, Object constants) {
        expect(this).use(pageFlow().dal()).constants(constants).should(expression);
    }

    boolean isVisible();
}
