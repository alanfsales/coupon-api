package com.coupon.mapper;

import com.coupon.dto.CouponDTO;
import com.coupon.domain.Coupon;
import com.coupon.dto.CouponResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public Coupon toEntity(CouponDTO dto) {
        return new Coupon(dto.code(), dto.description(), dto.discountValue(),
                dto.expirationDate(), dto.published());
    }

    public CouponResponseDTO toResponse(Coupon entity) {
        return new CouponResponseDTO(
                entity.getId(),
                entity.getCode(),
                entity.getDescription(),
                entity.getDiscountValue(),
                entity.getExpirationDate(),
                entity.getStatus(),
                entity.getPublished(),
                entity.isRedeemed()
        );
    }
}