package com.kantox.beverages.domain.entities;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Order {

    @Builder.Default
    private List<Product> products = new ArrayList<>();
    @Builder.Default
    private Double totalPrice = 0.0;

    public void addProduct(final Product product) {
        if (products == null) products = new ArrayList<>();
        products.add(product);
    }
}
