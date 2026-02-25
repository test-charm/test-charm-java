package org.testcharm.jfactory.cucumber;

import org.hibernate.Session;
import org.testcharm.dal.extensions.jdbc.DataBase;
import org.testcharm.dal.extensions.jdbc.DataBaseBuilder;
import org.testcharm.jfactory.JFactory;
import org.testcharm.jfactory.cucumber.factory.*;
import org.testcharm.jfactory.repo.JPADataRepository;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Persistence;
import java.util.Collection;

import static java.util.Arrays.asList;

public class EntityFactory extends JFactory {
    public static final EntityManager entityManager = Persistence.createEntityManagerFactory("jfactory-repo").createEntityManager();
    public static final JPADataRepository jpaDataRepository = new JPADataRepository(entityManager) {
        @Override
        public <T> Collection<T> queryAll(Class<T> type) {
            if (type.equals(DataBase.class)) {
                DataBaseBuilder builder = new DataBaseBuilder();
                return (Collection<T>) asList(builder.connect(
                        entityManager.unwrap(Session.class).doReturningWork(x -> x)));
            }
            return super.queryAll(type);
        }
    };

    public static EntityFactory runtimeInstance;

    public EntityFactory() {
        super(jpaDataRepository);
        runtimeInstance = this;

        register(Products.商品.class);
        register(Carts.购物车.class);
        register(ProductStocks.库存.class);
        register(Orders.订单.class);
        register(SnakeCaseProducts.SnakeCase商品.class);

        register(Products.ProductFactory.class);
        register(Carts.CartProduct.class);
        register(ProductStocks.Inventory.class);
        register(Orders.OrderFactory.class);

        register(Association.Company.class);
        register(Association.Department.class);
        register(Association.Employee.class);

        register(org.testcharm.jfactory.cucumber.factory.DataBase.class);

        ignoreDefaultValue(propertyWriter -> propertyWriter.getAnnotation(Id.class) != null);

        setSequenceStart(99999);
    }
}
