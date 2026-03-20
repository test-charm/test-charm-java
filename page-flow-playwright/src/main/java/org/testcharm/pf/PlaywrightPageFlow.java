package org.testcharm.pf;

import org.testcharm.io.TempDirectory;

public class PlaywrightPageFlow extends AbstractPageFlow {
    private final TempDirectory workingDir;

    public TempDirectory workingDir() {
        return workingDir;
    }

    public <B extends Builder<B, P>, P extends PlaywrightPageFlow> PlaywrightPageFlow(Builder<B, P> builder) {
        super(builder);
        workingDir = builder.workingDir;
    }

    public static Builder<?, ?> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, P>, P extends PlaywrightPageFlow> extends AbstractPageFlow.Builder<B, P> {
        private TempDirectory workingDir = new TempDirectory();

        @SuppressWarnings("unchecked")
        public B workingDir(TempDirectory workingDir) {
            this.workingDir = workingDir;
            return (B) this;
        }


        @SuppressWarnings("unchecked")
        @Override
        public P build() {
            return (P) new PlaywrightPageFlow(this);
        }
    }
}
