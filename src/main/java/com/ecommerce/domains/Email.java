package com.ecommerce.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Email {

    private final String to;
    private final String subject;
    private final String text;

}
