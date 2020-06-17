package com.ecommerce.domains;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Order {

    private final String id;
    private final Customer customer;
    private final LocalDate orderDate;
    private final Address address;
    private final List<Item> items;
    private final List<Promotion> benefits;
    private final BigDecimal orderValue;
    private BigDecimal taxes = new BigDecimal(0);
    private BigDecimal freightPrice = new BigDecimal(0);


    public BigDecimal getTotalOrder() {
        return items.stream()
                .map(Item::getFinalPrice)
                .reduce(new BigDecimal(0), BigDecimal::add)
                .add(freightPrice)
                .add(taxes);
    }

}
