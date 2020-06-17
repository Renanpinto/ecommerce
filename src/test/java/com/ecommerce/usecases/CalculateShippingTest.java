package com.ecommerce.usecases;

import com.ecommerce.builders.AddressBuilder;
import com.ecommerce.builders.CartBuilder;
import com.ecommerce.builders.ItemBuilder;
import com.ecommerce.domains.DigitalDelivery;
import com.ecommerce.domains.PaperMedia;
import com.ecommerce.domains.PhysicalDelivery;
import com.ecommerce.domains.Product;
import com.ecommerce.domains.enums.InformationUnitMeasurement;
import com.ecommerce.exceptions.FreightPriceException;
import com.ecommerce.gateways.ShippingCompany;
import com.ecommerce.gateways.client.CorreiosClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CalculateShippingTest {
    private CalculateShipping calculateShipping;
    private Product product;

    @Mock
    CorreiosClient correiosMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        ShippingCompany company = correiosMock;
        calculateShipping = new CalculateShipping(company);
    }

    @Test
    void test_calculate_physical_products() throws FreightPriceException {

        Product musicA = new Product("1A", "Música A", new BigDecimal(70), new DigitalDelivery(7, InformationUnitMeasurement.MB), true);
        Product movieB = new Product("2B", "Filme B", new BigDecimal(20), new DigitalDelivery(4, InformationUnitMeasurement.MB), true);
        product = new PaperMedia("3C", "Revista C", new BigDecimal(40), new PhysicalDelivery(40, 10, 10, 200), 200, false);

        var items = List.of(
                ItemBuilder.VALID(musicA, 1),
                ItemBuilder.VALID(movieB, 1),
                ItemBuilder.VALID(product, 1));

        var cart = CartBuilder.VALID(null, items, AddressBuilder.SP());

        when(correiosMock.getFreightPrice(any())).thenReturn(BigDecimal.valueOf(15));

        var shipping = calculateShipping.execute(cart);

        assertEquals(15, shipping.floatValue());
    }

    @Test
    void test_calculate_without_physical_products() throws FreightPriceException {

        Product musicA = new Product("1A", "Música A", new BigDecimal(70), new DigitalDelivery(7, InformationUnitMeasurement.MB), true);
        Product movieB = new Product("2B", "Filme B", new BigDecimal(20), new DigitalDelivery(4, InformationUnitMeasurement.MB), true);
        product = new PaperMedia("3C", "Revista C", new BigDecimal(40), new DigitalDelivery(5, InformationUnitMeasurement.MB), 200, true);

        var items = List.of(
                ItemBuilder.VALID(musicA, 1),
                ItemBuilder.VALID(movieB, 1),
                ItemBuilder.VALID(product, 1));

        var cart = CartBuilder.VALID(null, items, AddressBuilder.SP());

        when(correiosMock.getFreightPrice(any())).thenReturn(BigDecimal.valueOf(15));

        var shipping = calculateShipping.execute(cart);

        assertEquals(BigDecimal.ZERO, shipping);
    }

    @Test
    void test_exception_correio_integration() throws FreightPriceException {

        Product musicA = new Product("1A", "Música A", new BigDecimal(70), new DigitalDelivery(7, InformationUnitMeasurement.MB), true);
        Product movieB = new Product("2B", "Filme B", new BigDecimal(20), new DigitalDelivery(4, InformationUnitMeasurement.MB), true);
        product = new PaperMedia("3C", "Revista C", new BigDecimal(40), new PhysicalDelivery(40, 10, 10, 200), 200, false);

        var items = List.of(
                ItemBuilder.VALID(musicA, 1),
                ItemBuilder.VALID(movieB, 1),
                ItemBuilder.VALID(product, 1));

        var cart = CartBuilder.VALID(null, items, AddressBuilder.SP());

        when(correiosMock.getFreightPrice(any())).thenThrow(new FreightPriceException("message"));

        var shipping = calculateShipping.execute(cart);

        assertEquals(BigDecimal.ZERO, shipping);
    }

}
