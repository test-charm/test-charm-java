package org.testcharm.dal.extensions;

import org.testcharm.dal.DAL;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.dal.runtime.Order;

import static org.testcharm.dal.runtime.Order.BUILD_IN;

@Order(BUILD_IN)
public class Formatters implements Extension {

    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder()
                .registerValueFormat(new org.testcharm.dal.format.Formatters.String())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.URL())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.URI())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.Instant())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.LocalDate())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.LocalDateTime())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.Enum<>())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.Number())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.PositiveInteger())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.Integer())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.PositiveNumber())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.ZeroNumber())
                .registerValueFormat(new org.testcharm.dal.format.Formatters.Boolean())
        ;
    }
}
