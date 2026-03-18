package org.testcharm.dal.extensions.inspector.cucumber.page.e;

import com.microsoft.playwright.Locator;
import org.testcharm.pf.PlaywrightElement;
import org.testcharm.pf.PlaywrightPageFlow;

import static java.util.Arrays.binarySearch;

public class Element extends PlaywrightElement<Element, PlaywrightPageFlow> {
    public Element(PlaywrightPageFlow pf, Locator element) {
        super(pf, element);
    }

    @Override
    public boolean isInput() {
        return isCssCheckbox() || super.isInput();
    }

    @Override
    public Element fillIn(Object value) {
        if (isCssCheckbox()) {
            if (!value.equals(value()))
                click();
            return this;
        }
        return super.fillIn(value);
    }

    @Override
    public Object value() {
        if (isCssCheckbox())
            return css("input").single().value();
        return super.value();
    }

    private boolean isCssCheckbox() {
        return binarySearch((String[]) attribute("class"), "switch") >= 0;
    }
}
