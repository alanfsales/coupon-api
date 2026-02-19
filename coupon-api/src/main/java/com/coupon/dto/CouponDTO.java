package com.coupon.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponDTO(

        @NotBlank(message = "O campo code é obrigatório")
        String code,

        @NotBlank(message = "O campo description é obrigatória")
        @Size(max = 255, message = "O campo description deve conter no máximo 255 caracteres.")
        String description,

        @NotNull(message = "O campo discountValue é obrigatório")
        BigDecimal discountValue,

        @NotNull(message = "O campo  expirationDate é obrigatória")
        LocalDateTime expirationDate,

        boolean published

) {
}