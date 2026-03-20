package org.testcharm.pf;

import org.openqa.selenium.WebDriver;
import org.testcharm.io.TempDirectory;

public class SeleniumPageFlow extends AbstractPageFlow {
    private final WebDriver webDriver;

    public WebDriver webDriver() {
        return webDriver;
    }

    private final TempDirectory.Shared workingDir;

    public TempDirectory.Shared workingDir() {
        return workingDir;
    }

    public SeleniumPageFlow(Builder<?, ?> builder) {
        super(builder);
        webDriver = builder.webDriver;
        workingDir = builder.workingDir;
    }

    public static Builder<?, ?> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, P>, P extends SeleniumPageFlow> extends AbstractPageFlow.Builder<Builder<B, P>, SeleniumPageFlow> {
        private TempDirectory.Shared workingDir = new TempDirectory.Shared();
        private WebDriver webDriver;

        public B webDriver(WebDriver webDriver) {
            this.webDriver = webDriver;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B workingDir(TempDirectory.Shared workingDir) {
            this.workingDir = workingDir;
            return (B) this;
        }

        @Override
        public SeleniumPageFlow build() {
            return new SeleniumPageFlow(this);
        }
    }
}
