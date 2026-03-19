package org.testcharm.dal.extensions.inspector.cucumber.page;

import org.testcharm.dal.extensions.inspector.cucumber.page.e.Element;
import org.testcharm.dal.extensions.inspector.cucumber.page.e.Tab;

public class OutputPanel extends Tab {
    public OutputPanel(Element header, Element element) {
        super(header, element);
    }

    @Override
    public String toString() {
        return element().text();
    }
}
