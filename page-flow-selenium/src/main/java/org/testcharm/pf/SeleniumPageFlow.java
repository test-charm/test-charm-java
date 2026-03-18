package org.testcharm.pf;

import org.openqa.selenium.WebDriver;

public class SeleniumPageFlow extends AbstractPageFlow {
    private final WebDriver webDriver;

    public WebDriver webDriver() {
        return webDriver;
    }

    public SeleniumPageFlow(Builder builder) {
        super(builder);
        webDriver = builder.webDriver;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractPageFlow.Builder<Builder, SeleniumPageFlow> {
        private WebDriver webDriver;

        public Builder webDriver(WebDriver webDriver) {
            this.webDriver = webDriver;
            return this;
        }

        @Override
        public SeleniumPageFlow build() {
            return new SeleniumPageFlow(this);
        }
    }
}
