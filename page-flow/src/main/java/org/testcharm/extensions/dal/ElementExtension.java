package org.testcharm.extensions.dal;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Data;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.dal.runtime.JavaClassPropertyAccessor;
import org.testcharm.dal.runtime.checker.Checker;
import org.testcharm.dal.runtime.checker.CheckingContext;
import org.testcharm.pf.By;
import org.testcharm.pf.Element;
import org.testcharm.pf.Elements;
import org.testcharm.pf.WebElement;
import org.testcharm.util.BeanClass;

public class ElementExtension implements Extension {
    public static final Checker PHONY_CHECKER = new Checker() {
        @Override
        public boolean failed(CheckingContext checkingContext) {
            return false;
        }
    };

    @Override
    @SuppressWarnings("unchecked")
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder()
                .checkerSetForMatching()
                .register((expected, actual) -> actual.cast(Element.class)
                        .map(e -> inputToElement(expected, e)))
                .register((expected, actual) -> actual.cast(Elements.class)
                        .map(e -> inputToElement(expected, (Element<?, ?>) e.single())));

        dal.getRuntimeContextBuilder().registerPropertyAccessor(WebElement.class,
                new JavaClassPropertyAccessor<WebElement<?, ?>>((BeanClass) BeanClass.create(WebElement.class)) {
                    @Override
                    public Object getValue(WebElement<?, ?> webElement, Object property) {
                        if (property instanceof String && ((String) property).startsWith("@"))
                            return webElement.attribute(((String) property).substring(1));
                        return super.getValue(webElement, property);
                    }
                });

        dal.getRuntimeContextBuilder().registerDumper(By.class, byData -> (data, dumpingBuffer) ->
                dumpingBuffer.append(data.value().toString()));
    }

    private static Checker inputToElement(Data<?> expected, Element<?, ?> e) {
        if (e.isInput()) {
            e.fillIn(expected.value());
            return PHONY_CHECKER;
        }
        return null;
    }
}
