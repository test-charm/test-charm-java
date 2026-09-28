package org.testcharm.pf;

public interface WebElement<E extends WebElement<E, RE, PF>, RE, PF extends PageFlow> extends Element<E, RE, PF> {

    String[] EMPTY_STRING_ARRAY = new String[0];

    @Override
    default boolean isInput() {
        String tag = tag().toLowerCase();
        return tag.equals("textarea") || tag.equals("input") || tag.equals("select");
    }

    default Object attribute(String name) {
        String value = attributeValue(name);
        if (name.equals("class"))
            return value != null ? value.split(" ") : EMPTY_STRING_ARRAY;
        return value;
    }

    String attributeValue(String name);

    default boolean checkAble() {
        return "checkbox".equals(attributeValue("type"));
    }

    default boolean selectAble() {
        return "select".equals(tag());
    }

    @Override
    default Object value() {
        if (isInput())
            return attribute("value");
        return Element.super.value();
    }

    E download();
}
