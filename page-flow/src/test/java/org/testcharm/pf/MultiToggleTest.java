package org.testcharm.pf;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.testcharm.pf.Indicator.root;

class MultiToggleTest {
    public static class PageFlowBuilder extends AbstractPageFlow.Builder {

        @Override
        public PageFlow build() {
            return new AbstractPageFlow(this);
        }
    }

    public static abstract class ElementT implements Element<ElementT, WebElement, PageFlow> {
    }

    PageFlow pageFlow = new PageFlowBuilder().build();
    ElementT element = mock(ElementT.class);
    Page<?> page = mock(Page.class);
    MultiToggle multiToggle = new MultiToggle(pageFlow);

    @Nested
    class Launch {

        @Test
        void first_open() {
            Launcher<ElementT> launcher = spy(new Launcher<ElementT>() {

                @Override
                public Page<?> construct(ElementT e, Indicator indicator) {
                    assertTrue(element == e);
                    assertEquals(indicator.getId(), Collections.singletonList("root"));
                    return page;
                }

                @Override
                public ElementT element() {
                    return element;
                }
            });
            Indicator indicator = root("root");
            Page<?> launch = multiToggle.launch(indicator, launcher);

            assertTrue(launch == page);

            verify(launcher).open(indicator);
        }

        @Test
        void reopen_should_not_invoke_open_and_construct() {
            Launcher<ElementT> sameLauncher = spy(new Launcher<ElementT>() {

                @Override
                public Page<?> construct(ElementT e, Indicator indicator) {
                    fail();
                    return null;
                }

                @Override
                public ElementT element() {
                    fail();
                    return null;
                }
            });

            Indicator indicator = root("root");
            Page<?> launch = multiToggle.launch(indicator, Launcher.launcher(() -> {
            }, () -> element, (e, ind) -> page));

            Page<?> launch2 = multiToggle.launch(indicator, sameLauncher);

            assertTrue(launch == launch2);
            verify(sameLauncher, never()).construct(any(), any());
            verify(sameLauncher, never()).element();
        }

        @Test
        void more_reopen_pages() {
            Page<?> root1 = mock(Page.class);
            Page<?> root2 = mock(Page.class);

            Page<?> returnRoot1 = multiToggle.launch(root("root1"), Launcher.launcher(() -> {
            }, () -> null, (e, ind) -> root1));

            Page<?> returnRoot2 = multiToggle.launch(root("root2"), Launcher.launcher(() -> {
            }, () -> null, (e, ind) -> root2));

            Page<?> returnRoot3 = multiToggle.launch(root("root1"), Launcher.launcher(() -> {
            }, () -> null, (e, ind) -> mock(Page.class)));

            Page<?> returnRoot4 = multiToggle.launch(root("root2"), Launcher.launcher(() -> {
            }, () -> null, (e, ind) -> mock(Page.class)));

            assertTrue(root1 == returnRoot1);
            assertTrue(root1 == returnRoot3);

            assertTrue(root2 == returnRoot2);
            assertTrue(root2 == returnRoot4);
        }
    }
}
