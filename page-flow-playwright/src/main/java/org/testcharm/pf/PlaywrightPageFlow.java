package org.testcharm.pf;

import com.microsoft.playwright.Page;
import org.testcharm.io.TempDirectory;

public class PlaywrightPageFlow extends AbstractPageFlow {
    private final TempDirectory workingDir;
    private final Page page;

    public TempDirectory workingDir() {
        return workingDir;
    }

    public Page page() {
        return page;
    }

    public <B extends Builder<B, P>, P extends PlaywrightPageFlow> PlaywrightPageFlow(Builder<B, P> builder) {
        super(builder);
        workingDir = builder.workingDir;
        page = builder.page;
    }

    public static Builder<?, ?> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, P>, P extends PlaywrightPageFlow> extends AbstractPageFlow.Builder<B, P> {
        private TempDirectory workingDir = new TempDirectory();

        private Page page;

        @SuppressWarnings("unchecked")
        public B workingDir(TempDirectory workingDir) {
            this.workingDir = workingDir;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B page(Page page) {
            this.page = page;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public P build() {
            return (P) new PlaywrightPageFlow(this);
        }
    }
}
