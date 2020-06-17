package com.ecommerce.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum ICMS {
    SP("SP", BigDecimal.valueOf(18)),
    RJ("RJ", BigDecimal.valueOf(20)),
    PR("PR", BigDecimal.valueOf(17));

    private final String code;
    private final BigDecimal taxRate;

}
