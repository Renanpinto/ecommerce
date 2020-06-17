package com.ecommerce.usecases;

import com.ecommerce.domains.Cart;
import com.ecommerce.domains.Item;
import com.ecommerce.domains.PaperMedia;
import com.ecommerce.domains.enums.ICMS;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ApplyICMS {

    public BigDecimal execute(Cart cart) {
        var taxRate = ICMS.valueOf(cart.getAddress().getState()).getTaxRate();
        return getTotalTaxable(cart)
                .multiply(taxRate.divide(BigDecimal.valueOf(100L)));
    }

    private BigDecimal getTotalTaxable(Cart cart) {
        return cart.getItems()
                .stream()
                .filter(item -> !(item.getProduct() instanceof PaperMedia))
                .map(Item::getTotal)
                .reduce(new BigDecimal(0), BigDecimal::add);
    }
}
