package com.ecommerce.usecases;

import com.ecommerce.builders.*;
import com.ecommerce.domains.*;
import com.ecommerce.domains.enums.InformationUnitMeasurement;
import com.ecommerce.domains.enums.PromotionUnit;
import com.ecommerce.gateways.EmailGateway;
import com.ecommerce.gateways.PromotionGateway;
import com.ecommerce.gateways.ShippingCompany;
import com.ecommerce.usecases.crud.PromotionCrud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CheckoutTest {
    private Checkout checkout;
    private Cart cart;

    @Mock
    private EmailGateway emailGateway;

    @Mock
    ShippingCompany correiosMock;
    @Mock
    PromotionGateway promotionGateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        ApplyPromotion applyPromotion = new ApplyPromotion(new PromotionCrud(promotionGateway));
        CalculateShipping shipping = new CalculateShipping(correiosMock);
        SendEmail sendEmail = new SendEmail(emailGateway);

        checkout = new Checkout(sendEmail, applyPromotion, shipping, new ApplyICMS());

        Customer customer = CustomerBuilder.VALID(AddressBuilder.SP());

        Product bookA = new PaperMedia("sku-a", "Livro 1", new BigDecimal(130), new DigitalDelivery(9, InformationUnitMeasurement.MB), 700, true);
        Product musicB = new Product("sku-b", "Musica 1", new BigDecimal(40), new PhysicalDelivery(40, 25, 1, .2f), false);
        Product newspaperC = new PaperMedia("sku-c", "Jornal 1", new BigDecimal(15), new DigitalDelivery(17, InformationUnitMeasurement.MB), 400, true);
        Product movieD = new Product("sku-d", "Filme 1", new BigDecimal(20), new DigitalDelivery(7, InformationUnitMeasurement.GB), true);

        List<Item> items = List.of(
                ItemBuilder.VALID(bookA, 1),
                ItemBuilder.VALID(musicB, 1),
                ItemBuilder.VALID(newspaperC, 1),
                ItemBuilder.VALID(movieD, 1)
        );

        cart = CartBuilder.VALID(customer, items, AddressBuilder.SP());
    }

    @Test
    void test_checkout_promotion_without_coupon() throws Exception {
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID(10L, PromotionUnit.PERCENT, Set.of("sku-a", "sku-b"))));
        when(correiosMock.getFreightPrice(any())).thenReturn(BigDecimal.valueOf(15));

        Order order = checkout.execute(cart);

        verify(emailGateway, times(1)).send(any());
        assertEquals(10.8, order.getTaxes().doubleValue());
        assertEquals(15, order.getFreightPrice().doubleValue());
        assertEquals(213.8, order.getTotalOrder().doubleValue());
    }

    @Test
    void test_checkout_promotion_with_coupon() throws Exception {
        //promoção com cupom
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID_WITH_COUPON(10L, PromotionUnit.PERCENT, Set.of("sku-a", "sku-b"))));

        when(correiosMock.getFreightPrice(any())).thenReturn(BigDecimal.valueOf(15));

        //Carrinho sem coupon
        Order order = checkout.execute(cart);

        verify(emailGateway, times(1)).send(any());
        assertEquals(10.80, order.getTaxes().doubleValue());
        assertEquals(15, order.getFreightPrice().doubleValue());
        assertEquals(230.8, order.getTotalOrder().doubleValue());
    }

    @Test
    void test_checkout_promotion_with_coupon_promotion_applied() throws Exception {
        //Promoção com cupom
        when(promotionGateway.findAll())
                .thenReturn(List.of(PromotionBuilder.VALID_WITH_COUPON(10L, PromotionUnit.PERCENT, Set.of("sku-a", "sku-b"))));
        when(correiosMock.getFreightPrice(any())).thenReturn(BigDecimal.valueOf(15));

        //Carrinho com cupom
        cart.setCoupon("ABC123");
        Order order = checkout.execute(cart);

        verify(emailGateway, times(1)).send(any());
        assertEquals(10.80, order.getTaxes().doubleValue());
        assertEquals(15, order.getFreightPrice().doubleValue());
        assertEquals(213.8, order.getTotalOrder().doubleValue());
    }
}
