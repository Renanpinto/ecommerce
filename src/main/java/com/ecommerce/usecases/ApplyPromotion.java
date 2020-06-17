package com.ecommerce.usecases;

import com.ecommerce.domains.Cart;
import com.ecommerce.domains.Promotion;
import com.ecommerce.domains.enums.PromotionUnit;
import com.ecommerce.usecases.crud.PromotionCrud;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class ApplyPromotion {
    private final PromotionCrud promotionCrud;

    public Cart execute(Cart cart) {
        List<Promotion> matchedPromotions = new ArrayList<>();
        var promotions = promotionCrud.findAll()
                .stream()
                .filter(promo -> !promo.hasCoupon() || Objects.nonNull(cart.getCoupon()) && promo.getCoupon().contains(cart.getCoupon()))
                .max(Comparator.comparing(Promotion::getAmount));

        promotions.ifPresent(promo -> {
            cart.getItems()
                    .forEach(item -> {
                        if (promo.getSkus().contains(item.getProduct().getSku())) {
                            if (promo.getPromotionUnit().equals(PromotionUnit.NOMINAL)) {
                                item.setDiscount(promo.getAmount());
                            } else {
                                item.setDiscount(item.getTotal()
                                        .multiply(BigDecimal.valueOf(promo.getAmount())
                                                .divide(BigDecimal.valueOf(100L))).longValue());
                            }
                        }
                    });
            matchedPromotions.add(promo);
        });

        cart.setBenefits(matchedPromotions);
        return cart;
    }
}
