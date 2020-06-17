package com.ecommerce.domains;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Address {
    private final String cep;
    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
}
