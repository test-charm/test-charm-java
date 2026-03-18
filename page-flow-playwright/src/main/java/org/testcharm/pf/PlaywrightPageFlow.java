package org.testcharm.pf;

public class PlaywrightPageFlow extends AbstractPageFlow {

    public <B extends Builder<B, P>, P extends PlaywrightPageFlow> PlaywrightPageFlow(Builder<B, P> builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder<>();
    }

    public static class Builder<B extends Builder<B, P>, P extends PlaywrightPageFlow> extends AbstractPageFlow.Builder<B, P> {

        @SuppressWarnings("unchecked")
        @Override
        public P build() {
            return (P) new PlaywrightPageFlow(this);
        }
    }
}
