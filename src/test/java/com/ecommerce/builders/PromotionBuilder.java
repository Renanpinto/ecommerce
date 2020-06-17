package com.ecommerce.builders;

import com.ecommerce.domains.Promotion;
import com.ecommerce.domains.enums.PromotionUnit;

import java.util.Set;

public class PromotionBuilder {


    public static Promotion VALID(Long amount, PromotionUnit promotionUnit, Set<String> sku) {
        return Promotion.builder()
                .promotionUnit(promotionUnit)
                .amount(amount)
                .skus(sku)
                .build();
    }

    public static Promotion VALID_WITH_COUPON(Long amount, PromotionUnit promotionUnit, Set<String> sku) {
        return Promotion.builder()
                .promotionUnit(promotionUnit)
                .amount(amount)
                .coupon(Set.of("ABC123"))
                .skus(sku)
                .build();
    }

}
