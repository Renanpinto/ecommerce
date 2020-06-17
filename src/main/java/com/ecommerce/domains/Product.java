package com.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Product {

    private final String sku;
    private final String name;
    private final BigDecimal price;
    private AbstractDeliveryWay deliveryWay;
    private boolean digital;
}
