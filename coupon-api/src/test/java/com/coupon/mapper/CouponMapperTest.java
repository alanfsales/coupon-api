package com.coupon.mapper;

import com.coupon.domain.Coupon;
import com.coupon.dto.CouponDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CouponMapperTest {

    private CouponMapper mapper;

    private CouponDTO couponDTO;

    @BeforeEach
    void setUp() {
        mapper = new CouponMapper();
    }

    @Test
    void deveConverterDtoParaEntidade() {
       couponDTO = new CouponDTO("ABC-123", "Descrição do cupom", new BigDecimal("0.8"),
                LocalDateTime.now().plusDays(10), false);

        Coupon entity = mapper.toEntity(couponDTO);

        assertNotNull(entity);
        assertEquals("ABC123", entity.getCode());
        assertEquals(couponDTO.description(), entity.getDescription());
        assertEquals(couponDTO.discountValue(), entity.getDiscountValue());
        assertEquals(couponDTO.expirationDate(), entity.getExpirationDate());
        assertFalse(entity.getPublished());
    }

}