package org.testcharm.jfactory.cucumber.factory;

import org.testcharm.jfactory.Spec;
import org.testcharm.jfactory.cucumber.entity.Cart;

public class Carts {
    public static class 购物车 extends Spec<Cart> {
    }

    public static class CartProduct extends Spec<Cart> {
        @Override
        protected String getName() {
            return "Cart";
        }
    }
}
