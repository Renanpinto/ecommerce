package com.ecommerce.domains;

import com.ecommerce.domains.enums.CorreiosServiceCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class Cart {

    private final String storeId;
    private final Customer customer;
    private final List<Item> items;
    private String coupon;
    private Address address;
    private BigDecimal discount;
    private CorreiosServiceCode shippingCode;
    private List<Promotion> benefits;

    public BigDecimal getFinalPrice() {
        return items.stream()
                .map(Item::getFinalPrice)
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

}
