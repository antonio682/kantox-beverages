package com.kantox.beverages.application;

import com.kantox.beverages.domain.entities.Order;
import com.kantox.beverages.domain.entities.Product;
import com.kantox.beverages.domain.enums.Products;
import com.kantox.beverages.domain.exceptions.KantoxBeveragesApplicationException;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderBeveragesUseCaseTest {

    @Autowired
    public OrderBeveragesUseCase orderBeveragesUseCase;

    @Test
    void makeAnOrderWithAllTheAvailableDiscounts() {
        // GIVEN
        Order orderWithAllThePossibleDiscounts = Order.builder().build();

        Product greenTea = new Product(Products.GREEN_TEA.getName(), 2);
        greenTea.setPrice(3.11);
        orderWithAllThePossibleDiscounts.addProduct(greenTea);

        Product strawberries = new Product(Products.STRAWBERRIES.getName(), 3);
        strawberries.setPrice(5.00);
        orderWithAllThePossibleDiscounts.addProduct(strawberries);

        Product coffees = new Product(Products.COFFEE.getName(), 3);
        coffees.setPrice(11.23);
        orderWithAllThePossibleDiscounts.addProduct(coffees);
        // WHEN
        Either<KantoxBeveragesApplicationException, Order> processedOrder = orderBeveragesUseCase.apply(orderWithAllThePossibleDiscounts);
        // THEN
        Assertions.assertTrue(processedOrder.isRight());
        Product greenTeaProcessed = getProducts(processedOrder, Products.GREEN_TEA.getName());
        Assertions.assertEquals(3.11, greenTeaProcessed.getDiscount());
        Product strawberriesProcessed = getProducts(processedOrder, Products.STRAWBERRIES.getName());
        Assertions.assertEquals(1.50, strawberriesProcessed.getDiscount());
        Product coffeesProcessed = getProducts(processedOrder, Products.COFFEE.getName());
        Assertions.assertEquals(22.46, coffeesProcessed.getDiscount());
        Assertions.assertEquals(27.84, processedOrder.get().getTotalPrice());
    }

    @Test
    void makeAnOrderWithOnlyGreenTeaDiscount() {
        // GIVEN
        Order orderWithAllThePossibleDiscounts = Order.builder().build();

        Product greenTea = new Product(Products.GREEN_TEA.getName(), 2);
        greenTea.setPrice(3.11);
        orderWithAllThePossibleDiscounts.addProduct(greenTea);

        Product strawberries = new Product(Products.STRAWBERRIES.getName(), 2);
        strawberries.setPrice(5.00);
        orderWithAllThePossibleDiscounts.addProduct(strawberries);

        Product coffees = new Product(Products.COFFEE.getName(), 2);
        coffees.setPrice(11.23);
        orderWithAllThePossibleDiscounts.addProduct(coffees);
        // WHEN
        Either<KantoxBeveragesApplicationException, Order> processedOrder = orderBeveragesUseCase.apply(orderWithAllThePossibleDiscounts);
        // THEN
        Assertions.assertTrue(processedOrder.isRight());
        Product greenTeaProcessed = getProducts(processedOrder, Products.GREEN_TEA.getName());
        Assertions.assertEquals(3.11, greenTeaProcessed.getDiscount());
        Product strawberriesProcessed = getProducts(processedOrder, Products.STRAWBERRIES.getName());
        Assertions.assertEquals(0.0, strawberriesProcessed.getDiscount());
        Product coffeesProcessed = getProducts(processedOrder, Products.COFFEE.getName());
        Assertions.assertEquals(0.0, coffeesProcessed.getDiscount());
        Assertions.assertEquals(35.57, processedOrder.get().getTotalPrice());
    }

    @Test
    void makeAnOrderWithOnlyStrawberryDiscount() {
        // GIVEN
        Order orderWithAllThePossibleDiscounts = Order.builder().build();

        Product greenTea = new Product(Products.GREEN_TEA.getName(), 1);
        greenTea.setPrice(3.11);
        orderWithAllThePossibleDiscounts.addProduct(greenTea);

        Product strawberries = new Product(Products.STRAWBERRIES.getName(), 3);
        strawberries.setPrice(5.00);
        orderWithAllThePossibleDiscounts.addProduct(strawberries);

        Product coffees = new Product(Products.COFFEE.getName(), 2);
        coffees.setPrice(11.23);
        orderWithAllThePossibleDiscounts.addProduct(coffees);
        // WHEN
        Either<KantoxBeveragesApplicationException, Order> processedOrder = orderBeveragesUseCase.apply(orderWithAllThePossibleDiscounts);
        // THEN
        Assertions.assertTrue(processedOrder.isRight());
        Product greenTeaProcessed = getProducts(processedOrder, Products.GREEN_TEA.getName());
        Assertions.assertEquals(0, greenTeaProcessed.getDiscount());
        Product strawberriesProcessed = getProducts(processedOrder, Products.STRAWBERRIES.getName());
        Assertions.assertEquals(1.50, strawberriesProcessed.getDiscount());
        Product coffeesProcessed = getProducts(processedOrder, Products.COFFEE.getName());
        Assertions.assertEquals(0.0, coffeesProcessed.getDiscount());
        Assertions.assertEquals(39.07, processedOrder.get().getTotalPrice());
    }

    @Test
    void makeAnOrderWithOnlyCoffeeDiscount() {
        // GIVEN
        Order orderWithAllThePossibleDiscounts = Order.builder().build();

        Product greenTea = new Product(Products.GREEN_TEA.getName(), 2);
        greenTea.setPrice(3.11);
        orderWithAllThePossibleDiscounts.addProduct(greenTea);

        Product strawberries = new Product(Products.STRAWBERRIES.getName(), 2);
        strawberries.setPrice(5.00);
        orderWithAllThePossibleDiscounts.addProduct(strawberries);

        Product coffees = new Product(Products.COFFEE.getName(), 3);
        coffees.setPrice(11.23);
        orderWithAllThePossibleDiscounts.addProduct(coffees);
        // WHEN
        Either<KantoxBeveragesApplicationException, Order> processedOrder = orderBeveragesUseCase.apply(orderWithAllThePossibleDiscounts);
        // THEN
        Assertions.assertTrue(processedOrder.isRight());
        Product greenTeaProcessed = getProducts(processedOrder, Products.GREEN_TEA.getName());
        Assertions.assertEquals(0.0, greenTeaProcessed.getDiscount());
        Product strawberriesProcessed = getProducts(processedOrder, Products.STRAWBERRIES.getName());
        Assertions.assertEquals(0.0, strawberriesProcessed.getDiscount());
        Product coffeesProcessed = getProducts(processedOrder, Products.COFFEE.getName());
        Assertions.assertEquals(22.46, coffeesProcessed.getDiscount());
        Assertions.assertEquals(39.07, processedOrder.get().getTotalPrice());
    }

    private Product getProducts(final Either<KantoxBeveragesApplicationException, Order> processedOrder,
                                final String name) {

        return processedOrder.get().getProducts().stream()
                .filter(product -> name.equalsIgnoreCase(product.getName()))
                .findFirst().orElseThrow(KantoxBeveragesApplicationException::new);
    }

}