package com.ecommerce.gateways;

import com.ecommerce.domains.Product;

import java.util.List;

public interface ProductGateway {

    List<Product> findAll();

    List<Product> findById();

    void savePromotion(Product product);
}
