package org.testcharm.dal.extensions.inspector.cucumber.page.e;

import org.testcharm.pf.AbstractRegion;

public class Tab extends AbstractRegion<Element> {
    private final Element header;

    public Tab(Element header, Element element) {
        super(element);
        this.header = header;
    }

    public Element getHeader() {
        return header;
    }
}
