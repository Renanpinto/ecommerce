package com.ecommerce.builders;

import com.ecommerce.domains.Address;

public class AddressBuilder {

    public static Address SP() {
        return Address.builder()
                .cep("11725-050")
                .street("Street name")
                .number("123")
                .neighborhood("Bairro")
                .city("SP")
                .state("SP")
                .build();
    }

    public static Address PR() {
        return Address.builder()
                .cep("11725-050")
                .street("Street name")
                .number("123")
                .neighborhood("Bairro")
                .city("PR")
                .state("PR")
                .build();
    }

    public static Address RJ() {
        return Address.builder()
                .cep("11725-050")
                .street("Street name")
                .number("123")
                .neighborhood("Bairro")
                .city("RJ")
                .state("RJ")
                .build();
    }

}
