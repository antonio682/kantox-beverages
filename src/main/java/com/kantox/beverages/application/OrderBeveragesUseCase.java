package com.kantox.beverages.application;

import com.kantox.beverages.domain.OrderDiscountService;
import com.kantox.beverages.domain.entities.Order;
import com.kantox.beverages.domain.entities.Product;
import com.kantox.beverages.domain.exceptions.KantoxBeveragesApplicationException;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OrderBeveragesUseCase implements Function<Order, Either<KantoxBeveragesApplicationException, Order>> {

    private final OrderDiscountService orderDiscountService;

    @Override
    public Either<KantoxBeveragesApplicationException, Order> apply(final Order order) {

        List<Product> products = order.getProducts().stream()
                .map(orderDiscountService::getDiscount)
                .collect(Collectors.toList());

        return Either.right(Order.builder()
                .products(products)
                .totalPrice(orderDiscountService.calculatePrice(products))
                .build());
    }
}
