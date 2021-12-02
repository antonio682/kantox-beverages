package com.kantox.beverages.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Products {

    GREEN_TEA("GR1", "Green Tea"),
    STRAWBERRIES("SR1", "Strawberries"),
    COFFEE("CF1", "Coffee");

    private final String productCode;
    private final String name;

}
