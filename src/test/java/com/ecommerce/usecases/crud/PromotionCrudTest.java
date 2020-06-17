package com.ecommerce.usecases.crud;

import com.ecommerce.builders.PromotionBuilder;
import com.ecommerce.domains.enums.PromotionUnit;
import com.ecommerce.gateways.PromotionGateway;
import com.ecommerce.gateways.PromotionGatewayInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PromotionCrudTest {
    final PromotionGateway promotionGateway = new PromotionGatewayInMemory();
    PromotionCrud crud;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        crud = new PromotionCrud(promotionGateway);
    }

    @Test
    void test_save_promotion() {
        var promotion = PromotionBuilder.VALID(10L, PromotionUnit.PERCENT,
                Set.of("sku-a", "sku-b", "sku-c"));
        crud.savePromotion(promotion);

        var promotions = promotionGateway.findAll();

        assertTrue(promotions.contains(promotion));

    }

    @Test
    void test_find_promotions() {
        var promotion = PromotionBuilder.VALID(10L, PromotionUnit.PERCENT,
                Set.of("sku-a", "sku-b", "sku-c"));
        promotionGateway.savePromotion(promotion);

        var promotions = crud.findAll();

        assertTrue(promotions.contains(promotion));
    }
}
