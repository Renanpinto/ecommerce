package com.ecommerce.domains;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaperMedia extends Product {
    private final int pages;

    public PaperMedia(String sku, String name, BigDecimal price, AbstractDeliveryWay deliveryWay, int pages, boolean digital) {
        super(sku, name, price, deliveryWay, digital);
        this.pages = pages;
    }
}
