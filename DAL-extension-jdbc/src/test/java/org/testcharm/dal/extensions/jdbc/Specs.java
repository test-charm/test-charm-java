package org.testcharm.dal.extensions.jdbc;

import org.testcharm.jfactory.Global;
import org.testcharm.jfactory.Spec;

public class Specs {
    @Global
    public static class Order extends Spec<org.testcharm.dal.extensions.jdbc.entity.Order> {
    }

    @Global
    public static class Product extends Spec<org.testcharm.dal.extensions.jdbc.entity.Product> {
    }

    @Global
    public static class OrderLine extends Spec<org.testcharm.dal.extensions.jdbc.entity.OrderLine> {
        @Override
        public void main() {
            property("product").is(Product.class);
        }
    }

    @Global
    public static class Sku extends Spec<org.testcharm.dal.extensions.jdbc.entity.Sku> {
    }
}
