package com.coupon.domain;

import com.coupon.exception.BadRequestBusinessRuleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CouponTest {

    @Test
    void deveSanitizarCodigoRemovendoCaracteresEspeciais() {
        Coupon coupon = new Coupon("abc-123", "Descrição do cupom", new BigDecimal("0.8"),
                LocalDateTime.now().plusDays(10), false
        );

        assertEquals("ABC123", coupon.getCode());
    }

    @Test
    void deveLancarErroQuandoCodigoNaoTiverSeisCaracteres() {
        Assertions.assertThrows(BadRequestBusinessRuleException.class, () ->
                new Coupon(
                        "ABC-1",
                        "Descrição ",
                        new BigDecimal("0.8"),
                        LocalDateTime.now().plusDays(10),
                        false
                )
        );
    }

    @Test
    void deveLancarErroQuandoDescontoMenorQueMinimo() {
        Assertions.assertThrows(BadRequestBusinessRuleException.class, () ->
                new Coupon(
                        "ABC123",
                        "Descrição do cupom",
                        new BigDecimal("0.4"),
                        LocalDateTime.now().plusDays(10),
                        false
                )
        );
    }

    @Test
    void deveLancarErroQuandoDataExpiracaoNoPassado() {
        Assertions.assertThrows(BadRequestBusinessRuleException.class, () ->
                new Coupon(
                        "ABC123",
                        "Descrição",
                        new BigDecimal("1.0"),
                        LocalDateTime.now().minusDays(1),
                        false
                )
        );
    }

}