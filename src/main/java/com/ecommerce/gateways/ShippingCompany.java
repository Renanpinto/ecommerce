package com.ecommerce.gateways;


import com.ecommerce.domains.Cart;
import com.ecommerce.exceptions.FreightPriceException;

import java.math.BigDecimal;

public interface ShippingCompany {

    BigDecimal getFreightPrice(Cart cart) throws FreightPriceException;
}
