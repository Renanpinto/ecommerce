package com.ecommerce.usecases.crud;

import com.ecommerce.domains.Promotion;
import com.ecommerce.gateways.PromotionGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PromotionCrud {
    private final PromotionGateway promotionGateway;

    public void savePromotion(Promotion promotion) {
        promotionGateway.savePromotion(promotion);
    }

    public List<Promotion> findAll() {
        return promotionGateway.findAll();
    }
}
