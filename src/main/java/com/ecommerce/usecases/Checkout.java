package com.ecommerce.usecases;


import com.ecommerce.domains.Cart;
import com.ecommerce.domains.Order;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class Checkout {
    private final SendEmail sendEmail;
    private final ApplyPromotion applyPromotion;
    private final CalculateShipping calculateShipping;
    private final ApplyICMS applyICMS;

    public Order execute(Cart cart) throws Exception {

        var newCart = applyPromotion.execute(cart);

        var order = buildOrder(newCart);

        sendEmail.execute(order);

        return order;
    }

    private Order buildOrder(Cart cart) {
        return Order.builder()
                .id(UUID.randomUUID().toString())
                .address(cart.getAddress())
                .customer(cart.getCustomer())
                .orderDate(LocalDate.now())
                .items(cart.getItems())
                .freightPrice(calculateShipping.execute(cart))
                .taxes(applyICMS.execute(cart))
                .benefits(cart.getBenefits())
                .orderValue(cart.getFinalPrice())
                .build();
    }
}
