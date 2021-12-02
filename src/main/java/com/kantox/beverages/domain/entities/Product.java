package com.kantox.beverages.domain.entities;

import lombok.Data;

@Data
public class Product {

    private String productCode;
    private String name;
    private Double price;
    private Integer quantity;
    private double discount;

    public Product(final String name, final Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
