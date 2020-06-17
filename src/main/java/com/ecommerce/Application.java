package com.ecommerce;

import com.ecommerce.domains.*;
import com.ecommerce.domains.enums.CorreiosServiceCode;
import com.ecommerce.domains.enums.InformationUnitMeasurement;
import com.ecommerce.domains.enums.PromotionUnit;
import com.ecommerce.gateways.client.CorreiosClient;
import com.ecommerce.gateways.client.EmailClient;
import com.ecommerce.gateways.impl.HttpService;
import com.ecommerce.gateways.impl.PromotionGatewayImpl;
import com.ecommerce.usecases.*;
import com.ecommerce.usecases.crud.PromotionCrud;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class Application {

    public static void main(String[] args) throws Exception {
        var promotionGateway = new PromotionGatewayImpl();
        var promotionCrud = new PromotionCrud(promotionGateway);
        var shippingCompany = new CorreiosClient(new HttpService());
        var sendEmail = new SendEmail(new EmailClient());
        var applyPromotion = new ApplyPromotion(promotionCrud);
        var calculateShipping = new CalculateShipping(shippingCompany);
        var applyICMS = new ApplyICMS();
        var checkout = new Checkout(sendEmail, applyPromotion, calculateShipping, applyICMS);

        promotionCrud.savePromotion(buildPromotion(5L, PromotionUnit.PERCENT, Set.of("sku-a", "sku-b")));

        Customer customer = buildCustomer();

        Product bookA = new PaperMedia("sku-a", "Livro 1", new BigDecimal(130), new DigitalDelivery(9, InformationUnitMeasurement.MB), 700, true);
        Product musicB = new Product("sku-b", "Musica 1", new BigDecimal(40), new PhysicalDelivery(40, 25, 1, .2f), false);
        Product newspaperC = new PaperMedia("sku-c", "Jornal 1", new BigDecimal(15), new DigitalDelivery(17, InformationUnitMeasurement.MB), 400, true);
        Product movieD = new Product("sku-d", "Filme 1", new BigDecimal(20), new DigitalDelivery(7, InformationUnitMeasurement.GB), true);

        List<Item> items = List.of(
                buildItem(bookA, 1),
                buildItem(musicB, 1),
                buildItem(newspaperC, 1),
                buildItem(movieD, 2)
        );

        Cart cart = buildCart(customer, items);

        Order order = checkout.execute(cart);

        System.out.println(new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(order));
    }

    private static Customer buildCustomer() {
        return Customer.builder()
                .cpf("123.456.789-00")
                .name("Renan")
                .email("renanspinto@hotmail.com")
                .address(Address.builder()
                        .cep("11725-050")
                        .street("Street name")
                        .number("123")
                        .neighborhood("Bairro")
                        .city("SP")
                        .state("SP")
                        .build())
                .build();
    }

    private static Promotion buildPromotion(Long amount, PromotionUnit promotionUnit, Set<String> sku) {
        return Promotion.builder()
                .name("Promoção 5%")
                .promotionUnit(promotionUnit)
                .amount(amount)
                .coupon(Set.of("ABC123"))
                .skus(sku)
                .build();
    }

    private static Item buildItem(Product product, Integer quantity) {
        return Item.builder()
                .product(product)
                .quantity(quantity)
                .build();
    }

    private static Cart buildCart(Customer customer, List<Item> items) {

        return Cart.builder()
                .customer(customer)
                .items(items)
                .coupon("ABC123")
                .shippingCode(CorreiosServiceCode.CODE_04014)
                .address(Address.builder()
                        .cep("11725-050")
                        .street("Street name")
                        .number("123")
                        .neighborhood("Bairro")
                        .city("SP")
                        .state("SP")
                        .build())
                .build();
    }

}
