package com.ecommerce.usecases;

import com.ecommerce.builders.AddressBuilder;
import com.ecommerce.builders.CartBuilder;
import com.ecommerce.builders.ItemBuilder;
import com.ecommerce.domains.Cart;
import com.ecommerce.domains.PaperMedia;
import com.ecommerce.domains.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ICMSTaxActionTest {
    private final ApplyICMS applyICMS = new ApplyICMS();
    private Cart cart;

    @Test
    void test_execute_icms_SP() {
        Product movieA = new Product("1A", "Filme A", BigDecimal.valueOf(10L), null, false);
        Product bookB = new PaperMedia("2B", "Livro B", BigDecimal.valueOf(20L), null, 100, false);
        Product bookC = new PaperMedia("3C", "Livro C", BigDecimal.valueOf(30L), null, 600, false);

        var items = List.of(
                ItemBuilder.VALID(movieA, 3),
                ItemBuilder.VALID(bookB, 2),
                ItemBuilder.VALID(bookC, 1)
        );

        cart = CartBuilder.VALID(null, items, AddressBuilder.SP());
        var tax = applyICMS.execute(cart);

        assertEquals(5L, tax.longValue());
    }

    @Test
    void test_execute_icms_PR() {
        Product movieA = new Product("1A", "Filme A", BigDecimal.valueOf(10L), null, false);
        Product bookB = new PaperMedia("2B", "Livro B", BigDecimal.valueOf(20L), null, 100, false);
        Product bookC = new PaperMedia("3C", "Livro C", BigDecimal.valueOf(30L), null, 600, false);

        var items = List.of(
                ItemBuilder.VALID(movieA, 3),
                ItemBuilder.VALID(bookB, 2),
                ItemBuilder.VALID(bookC, 1)
        );

        cart = CartBuilder.VALID(null, items, AddressBuilder.PR());
        var tax = applyICMS.execute(cart);

        assertEquals(5L, tax.longValue());
    }

    @Test
    void test_execute_icms_RJ() {
        Product movieA = new Product("1A", "Filme A", BigDecimal.valueOf(10L), null, false);
        Product bookB = new PaperMedia("2B", "Livro B", BigDecimal.valueOf(20L), null, 100, false);
        Product bookC = new PaperMedia("3C", "Livro C", BigDecimal.valueOf(30L), null, 600, false);

        var items = List.of(
                ItemBuilder.VALID(movieA, 3),
                ItemBuilder.VALID(bookB, 2),
                ItemBuilder.VALID(bookC, 1)
        );

        cart = CartBuilder.VALID(null, items, AddressBuilder.RJ());
        var tax = applyICMS.execute(cart);

        assertEquals(6L, tax.longValue());
    }
}
