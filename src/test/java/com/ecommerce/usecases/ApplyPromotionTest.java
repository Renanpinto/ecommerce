package com.ecommerce.usecases;

import com.ecommerce.builders.AddressBuilder;
import com.ecommerce.builders.CartBuilder;
import com.ecommerce.builders.ItemBuilder;
import com.ecommerce.builders.PromotionBuilder;
import com.ecommerce.domains.Cart;
import com.ecommerce.domains.Item;
import com.ecommerce.domains.Product;
import com.ecommerce.domains.enums.PromotionUnit;
import com.ecommerce.gateways.PromotionGateway;
import com.ecommerce.usecases.crud.PromotionCrud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ApplyPromotionTest {
    @Mock
    PromotionGateway promotionGateway;

    private ApplyPromotion applyPromotion;
    private Cart shoppingCart;


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        applyPromotion = new ApplyPromotion(new PromotionCrud(promotionGateway));

        Product bookA = new Product("sku-a", "Livro A", BigDecimal.valueOf(10L), null, false);
        Product bookB = new Product("sku-b", "Livro B", BigDecimal.valueOf(20L), null, false);
        Product bookC = new Product("sku-c", "Livro C", BigDecimal.valueOf(30L), null, false);

        List<Item> items = List.of(ItemBuilder.VALID(bookA, 3),
                ItemBuilder.VALID(bookB, 2),
                ItemBuilder.VALID(bookC, 1));
        shoppingCart = CartBuilder.VALID(null, items, AddressBuilder.SP());
    }

    @Test
    void test_apply_promotion_with_coupon_for_product_ABC123() {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID_WITH_COUPON(10L, PromotionUnit.PERCENT, Set.of("sku-a"))));
        shoppingCart.setCoupon("ABC123");

        var cartOutput = applyPromotion.execute(shoppingCart);
        assertEquals(3, cartOutput.getItems().get(0).getDiscount());
        assertEquals(0, cartOutput.getItems().get(1).getDiscount());
        assertEquals(0, cartOutput.getItems().get(2).getDiscount());
        assertEquals(97, cartOutput.getFinalPrice().intValue());
    }

    @Test
    void test_execute_with_coupon_for_all_products() {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID(10L, PromotionUnit.PERCENT,
                        Set.of("sku-a", "sku-b", "sku-c"))));

        shoppingCart.setCoupon("ABC123");

        var cartOutput = applyPromotion.execute(shoppingCart);

        assertEquals(cartOutput.getItems().get(0).getDiscount(), 3);
        assertEquals(cartOutput.getItems().get(1).getDiscount(), 4);
        assertEquals(cartOutput.getItems().get(2).getDiscount(), 3);
        assertEquals(90, cartOutput.getFinalPrice().intValue());
    }

    @Test
    void test_apply_promotion_without_coupon_SkuA() {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID(15L, PromotionUnit.PERCENT,
                        Set.of("sku-a"))));

        var cartOutput = applyPromotion.execute(shoppingCart);

        assertEquals(4, cartOutput.getItems().get(0).getDiscount());
        assertEquals(0, cartOutput.getItems().get(1).getDiscount());
        assertEquals(0, cartOutput.getItems().get(2).getDiscount());
        assertEquals(96, cartOutput.getFinalPrice().intValue());
    }

    @Test
    void test_apply_promotion_without_coupon() {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID(15L, PromotionUnit.PERCENT,
                        Set.of("sku-a", "sku-b", "sku-c"))));

        var cartOutput = applyPromotion.execute(shoppingCart);

        assertEquals(4, cartOutput.getItems().get(0).getDiscount());
        assertEquals(6, cartOutput.getItems().get(1).getDiscount());
        assertEquals(4, cartOutput.getItems().get(2).getDiscount());
        assertEquals(86, cartOutput.getFinalPrice().intValue());
    }

    @Test
    void test_apply_promotion_with_coupon_for_product_ABC123_nominal() {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID_WITH_COUPON(10L, PromotionUnit.NOMINAL,
                        Set.of("sku-a"))));
        shoppingCart.setCoupon("ABC123");

        var cartOutput = applyPromotion.execute(shoppingCart);
        assertEquals(10, cartOutput.getItems().get(0).getDiscount());
        assertEquals(0, cartOutput.getItems().get(1).getDiscount());
        assertEquals(0, cartOutput.getItems().get(2).getDiscount());
        assertEquals(90, cartOutput.getFinalPrice().intValue());
    }

    @Test
    void test_execute_with_coupon_for_all_products_nominal() {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID(10L, PromotionUnit.NOMINAL,
                        Set.of("sku-a", "sku-b", "sku-c"))));

        shoppingCart.setCoupon("ABC123");

        var cartOutput = applyPromotion.execute(shoppingCart);

        assertEquals(10, cartOutput.getItems().get(0).getDiscount());
        assertEquals(10, cartOutput.getItems().get(1).getDiscount());
        assertEquals(10, cartOutput.getItems().get(2).getDiscount());
        assertEquals(70, cartOutput.getFinalPrice().intValue());
    }

    @Test
    void test_apply_promotion_without_coupon_SkuA_nominal() {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID(5L, PromotionUnit.NOMINAL,
                        Set.of("sku-a"))));

        var cartOutput = applyPromotion.execute(shoppingCart);

        assertEquals(5, cartOutput.getItems().get(0).getDiscount());
        assertEquals(0, cartOutput.getItems().get(1).getDiscount());
        assertEquals(0, cartOutput.getItems().get(2).getDiscount());
        assertEquals(95, cartOutput.getFinalPrice().intValue());
    }

    @Test
    void test_apply_promotion_without_coupon_nominal() {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID(5L, PromotionUnit.NOMINAL,
                        Set.of("sku-a", "sku-b", "sku-c"))));

        var cartOutput = applyPromotion.execute(shoppingCart);

        assertEquals(5, cartOutput.getItems().get(0).getDiscount());
        assertEquals(5, cartOutput.getItems().get(1).getDiscount());
        assertEquals(5, cartOutput.getItems().get(2).getDiscount());
        assertEquals(85, cartOutput.getFinalPrice().intValue());
    }
}
