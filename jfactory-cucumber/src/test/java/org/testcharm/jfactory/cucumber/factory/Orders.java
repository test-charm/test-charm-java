package org.testcharm.jfactory.cucumber.factory;

import org.testcharm.jfactory.Spec;
import org.testcharm.jfactory.cucumber.entity.Order;

public class Orders {
    public static class 订单 extends Spec<Order> {
    }

    public static class OrderFactory extends Spec<Order> {
        @Override
        protected String getName() {
            return "Order";
        }
    }
}
