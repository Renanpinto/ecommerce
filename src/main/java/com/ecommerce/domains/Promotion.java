package com.ecommerce.domains;

import com.ecommerce.domains.enums.PromotionUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class Promotion {

    private final String uuid;
    private final String name;
    private final BigDecimal storeId;
    private final PromotionUnit promotionUnit;
    private final long amount;
    private final Set<String> coupon;
    private final Set<String> skus;

    public boolean hasCoupon() {
        return !ObjectUtils.isEmpty(coupon);
    }

}
