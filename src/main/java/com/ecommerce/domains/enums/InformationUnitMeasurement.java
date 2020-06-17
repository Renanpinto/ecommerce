package com.ecommerce.domains.enums;

import lombok.Getter;

@Getter
public enum InformationUnitMeasurement {
    B(1),
    KB(1024 ^ 1),
    MB(1024 ^ 2),
    GB(1024 ^ 3);

    private final long size;

    InformationUnitMeasurement(long size) {
        this.size = size;
    }
}
