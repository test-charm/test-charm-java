package org.testcharm.pf;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.testcharm.pf.Indicator.root;

class SingleActiveTest {

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
    SingleActive singleActive = new SingleActive(pageFlow);

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
            Page<?> launch = singleActive.launch(indicator, launcher);

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
            Page<?> launch = singleActive.launch(indicator, Launcher.launcher(() -> {
            }, () -> element, (e, ind) -> page));

            Page<?> launch2 = singleActive.launch(indicator, sameLauncher);

            assertTrue(launch == launch2);
            verify(sameLauncher, never()).construct(any(), any());
            verify(sameLauncher, never()).element();
        }

        @Test
        void open_another_one() {
            ElementT anotherElement = mock(ElementT.class);
            Page<?> anotherPage = mock(Page.class);

            Launcher<ElementT> sameLauncher = spy(new Launcher<ElementT>() {

                @Override
                public Page<?> construct(ElementT e, Indicator indicator) {
                    assertTrue(e == anotherElement);
                    assertEquals(indicator.getId(), Collections.singletonList("root2"));
                    return anotherPage;
                }

                @Override
                public ElementT element() {
                    return anotherElement;
                }
            });

            singleActive.launch(root("root"), Launcher.launcher(() -> {
            }, () -> element, (e, ind) -> page));

            Indicator indicator = root("root2");
            Page<?> launch2 = singleActive.launch(indicator, sameLauncher);

            assertTrue(launch2 == anotherPage);
            verify(sameLauncher).open(indicator);
        }


        @Test
        void create_page_instance_by_page_factory() {
            Page<?> pageByFactory = mock(Page.class);

            pageFlow.pageFactory().register("id= [root]", (e, indicator) -> {
                assertTrue(element == e);
                return pageByFactory;
            });

            Page<?> launch = singleActive.launch(root("root"), Launcher.launcher(() -> {
            }, () -> element, (e, ind) -> page));

            assertTrue(launch == pageByFactory);
        }

        @Test
        void miss_matched() {
            Page<?> pageByFactory = mock(Page.class);

            pageFlow.pageFactory().register("id= [admin]", (e, indicator) -> {
                assertTrue(element == e);
                return pageByFactory;
            });

            Page<?> launch = singleActive.launch(root("root"), Launcher.launcher(() -> {
            }, () -> element, (e, ind) -> page));

            assertTrue(launch == page);
        }
    }
}