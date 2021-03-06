package com.ecommerce.gateways.impl;

import com.ecommerce.domains.Promotion;
import com.ecommerce.gateways.PromotionGateway;

import java.util.ArrayList;
import java.util.List;

public class PromotionGatewayImpl implements PromotionGateway {

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
