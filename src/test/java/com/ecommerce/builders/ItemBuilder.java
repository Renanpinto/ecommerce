package com.ecommerce.builders;

import com.ecommerce.domains.Item;
import com.ecommerce.domains.Product;

public class ItemBuilder {


    public static Item VALID(Product product, Integer quantity) {
        return Item.builder()
                .product(product)
                .quantity(quantity)
                .build();
    }

}
