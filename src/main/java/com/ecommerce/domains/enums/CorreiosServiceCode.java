package com.ecommerce.domains.enums;

import lombok.Getter;

@Getter
public enum CorreiosServiceCode {
    CODE_04510("04510", "PAC sem contrato"),
    CODE_04014("04014", "SEDEX sem contrato");

    private final String code;
    private final String name;

    CorreiosServiceCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
