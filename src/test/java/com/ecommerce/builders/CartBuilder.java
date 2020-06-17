package com.ecommerce.builders;

import com.ecommerce.domains.Address;
import com.ecommerce.domains.Cart;
import com.ecommerce.domains.Customer;
import com.ecommerce.domains.Item;
import com.ecommerce.domains.enums.CorreiosServiceCode;

import java.util.List;

public class CartBuilder {
    public static Cart VALID(Customer customer, List<Item> items, Address address) {
        return Cart.builder()
                .storeId("StoreId")
                .customer(customer)
                .items(items)
                .address(address)
                .shippingCode(CorreiosServiceCode.CODE_04510)
                .build();
    }
}
