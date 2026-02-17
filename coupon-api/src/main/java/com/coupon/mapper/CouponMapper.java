package com.coupon.mapper;

import com.coupon.dto.CouponDTO;
import com.coupon.domain.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public Coupon toEntity(CouponDTO dto) {
        return new Coupon(dto.code(), dto.description(), dto.discountValue(),
                dto.expirationDate(), dto.published());
    }
}