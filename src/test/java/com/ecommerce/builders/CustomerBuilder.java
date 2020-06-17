package com.ecommerce.builders;

import com.ecommerce.domains.Address;
import com.ecommerce.domains.Customer;

public class CustomerBuilder {


    public static Customer VALID(Address address) {
        return Customer.builder()
                .cpf("123.456.789-00")
                .name("Nome")
                .email("nome@email.com")
                .address(address)
                .build();
    }

}
