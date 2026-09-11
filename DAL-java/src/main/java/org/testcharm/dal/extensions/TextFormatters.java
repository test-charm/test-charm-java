package org.testcharm.dal.extensions;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.dal.runtime.Order;
import org.testcharm.dal.runtime.TextFormatter;

import static org.testcharm.dal.runtime.Order.BUILD_IN;

@Order(BUILD_IN)
public class TextFormatters implements Extension {

    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder()
                .registerTextFormatter("NL", TextFormatter.NL)
                .registerTextFormatter("LF", TextFormatter.LF_NL)
                .registerTextFormatter("CR", TextFormatter.CR_NL)
                .registerTextFormatter("CRLF", TextFormatter.CRLF_NL)
                .registerTextFormatter("<", TextFormatter.LT_EOL)
                .registerTextFormatter("\\", TextFormatter.BSL_CONT)
                .registerTextFormatter("⏎", TextFormatter.RET_EOL);
    }
}
