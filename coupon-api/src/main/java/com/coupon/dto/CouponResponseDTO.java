package com.coupon.dto;

import com.coupon.domain.CouponStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CouponResponseDTO(
        UUID id,
        String code,
        String description,
        BigDecimal discountValue,
        LocalDateTime expirationDate,
        CouponStatus status,
        boolean published,
        boolean redeemed
) {}