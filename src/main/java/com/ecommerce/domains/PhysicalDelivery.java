package com.ecommerce.domains;

public class PhysicalDelivery extends AbstractDeliveryWay {
    private final float height;
    private final float width;
    private final float length;
    private final float weight;

    public PhysicalDelivery(float height, float width, float length, float weight) {
        this.height = height;
        this.width = width;
        this.length = length;
        this.weight = weight;
    }
}
