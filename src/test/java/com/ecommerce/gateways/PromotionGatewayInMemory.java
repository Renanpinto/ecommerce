package com.ecommerce.gateways;

import com.ecommerce.domains.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionGatewayInMemory implements PromotionGateway {
    final List<Promotion> promotions = new ArrayList<>();

    @Override
    public void savePromotion(Promotion promotion) {
        promotions.add(promotion);
    }

    @Override
    public List<Promotion> findAll() {
        return promotions;
    }
}
