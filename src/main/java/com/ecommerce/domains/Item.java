package com.ecommerce.domains;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Product product;
    private Integer quantity;
    private long discount;

    public BigDecimal getTotal() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    public BigDecimal getFinalPrice() {
        return product.getPrice()
                .multiply(new BigDecimal(quantity))
                .subtract(BigDecimal.valueOf(discount));
    }

}

