package org.testcharm.pf;

public class PlaywrightPageFlow extends AbstractPageFlow {
    private final FileManager fileManager;

    public FileManager file() {
        return fileManager;
    }

    public <B extends Builder<B, P>, P extends PlaywrightPageFlow> PlaywrightPageFlow(Builder<B, P> builder) {
        super(builder);
        fileManager = builder.fileManager;
    }

    public static Builder<?, ?> builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, P>, P extends PlaywrightPageFlow> extends AbstractPageFlow.Builder<B, P> {
        private FileManager fileManager = new FileManager();

        @SuppressWarnings("unchecked")
        public B fileManager(FileManager fileManager) {
            this.fileManager = fileManager;
            return (B) this;
        }


        @SuppressWarnings("unchecked")
        @Override
        public P build() {
            return (P) new PlaywrightPageFlow(this);
        }
    }
}
