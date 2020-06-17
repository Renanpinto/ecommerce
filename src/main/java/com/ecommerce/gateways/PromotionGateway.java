package com.ecommerce.gateways;

import com.ecommerce.domains.Promotion;

import java.util.List;

public interface PromotionGateway {

    List<Promotion> findAll();

    void savePromotion(Promotion promotion);
}
