package com.ecommerce.gateways.client;

import com.ecommerce.builders.AddressBuilder;
import com.ecommerce.builders.CartBuilder;
import com.ecommerce.builders.CustomerBuilder;
import com.ecommerce.domains.Cart;
import com.ecommerce.domains.Customer;
import com.ecommerce.domains.enums.CorreiosServiceCode;
import com.ecommerce.exceptions.FreightPriceException;
import com.ecommerce.gateways.impl.HttpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CorreiosTest {
    private CorreiosClient correios;
    private Cart cart;

    @BeforeEach
    void setup() {
        correios = new CorreiosClient(new HttpService());

        Customer customer = CustomerBuilder.VALID(AddressBuilder.SP());

        cart = CartBuilder.VALID(customer, null, AddressBuilder.SP());
    }

    @Test
    void test_get_correio_integration() throws FreightPriceException {
        BigDecimal price = correios.getFreightPrice(cart);

        assertEquals(21, price.doubleValue());
    }

    @Test
    void test_get_correio_integration_() throws FreightPriceException {
        cart.setShippingCode(CorreiosServiceCode.CODE_04014);
        BigDecimal price = correios.getFreightPrice(cart);

        assertEquals(21, price.doubleValue());
    }

}
