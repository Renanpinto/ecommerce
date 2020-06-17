package com.ecommerce.domains;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class Customer {

    private final String cpf;
    private final String name;
    private final String email;
    private final Address address;

}
