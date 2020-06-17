package com.ecommerce.usecases;


import com.ecommerce.domains.Cart;
import com.ecommerce.domains.Item;
import com.ecommerce.exceptions.FreightPriceException;
import com.ecommerce.gateways.ShippingCompany;

import java.math.BigDecimal;

public class CalculateShipping {
    private final ShippingCompany company;

    public CalculateShipping(ShippingCompany company) {
        this.company = company;
    }

    public BigDecimal execute(Cart cart) {
        BigDecimal shippingValue = BigDecimal.ZERO;
        if (hasSomeMaterialProduct(cart)) {
            try {
                shippingValue = company.getFreightPrice(cart);
            } catch (FreightPriceException e) {
                System.out.print("Http error");
            }
        }
        return shippingValue;
    }

    private boolean hasSomeMaterialProduct(Cart cart) {
        return cart.getItems()
                .stream()
                .map(Item::getProduct)
                .anyMatch(product -> !product.isDigital());
    }
}
