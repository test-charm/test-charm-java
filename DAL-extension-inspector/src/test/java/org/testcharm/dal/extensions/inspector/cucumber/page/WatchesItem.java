package org.testcharm.dal.extensions.inspector.cucumber.page;

import org.testcharm.dal.extensions.inspector.cucumber.page.e.Element;
import org.testcharm.pf.AbstractRegion;
import org.testcharm.pf.Elements;

public class WatchesItem extends AbstractRegion<Element> {
    public WatchesItem(Element element) {
        super(element);
    }

    @Override
    public String toString() {
        return perform("css[.watches-item-content].text");
    }

    public Elements<Element> image() {
        return locate("css[img]");
    }

    public Elements<Element> download() {
        return locate("css[a]");
    }
}
