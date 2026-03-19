package org.testcharm.pf;

import org.openqa.selenium.WebDriver;
import org.testcharm.io.FileManager;

public class SeleniumPageFlow extends AbstractPageFlow {
    private final WebDriver webDriver;

    public WebDriver webDriver() {
        return webDriver;
    }

    private final FileManager.Shared fileManager;

    public FileManager.Shared file() {
        return fileManager;
    }

    public SeleniumPageFlow(Builder<?, ?> builder) {
        super(builder);
        webDriver = builder.webDriver;
        fileManager = builder.fileManager;
    }

    public static Builder<?, ?> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, P>, P extends SeleniumPageFlow> extends AbstractPageFlow.Builder<Builder<B, P>, SeleniumPageFlow> {
        private FileManager.Shared fileManager = new FileManager.Shared();
        private WebDriver webDriver;

        public B webDriver(WebDriver webDriver) {
            this.webDriver = webDriver;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B fileManager(FileManager.Shared fileManager) {
            this.fileManager = fileManager;
            return (B) this;
        }

        @Override
        public SeleniumPageFlow build() {
            return new SeleniumPageFlow(this);
        }
    }
}
