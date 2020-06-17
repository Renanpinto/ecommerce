package com.ecommerce.domains;

import com.ecommerce.domains.enums.InformationUnitMeasurement;

public class DigitalDelivery extends AbstractDeliveryWay {
    private final float downloadSize;
    private final InformationUnitMeasurement sizeUnit;

    public DigitalDelivery(float downloadSize, InformationUnitMeasurement sizeUnit) {
        this.downloadSize = downloadSize;
        this.sizeUnit = sizeUnit;
    }
}
