package com.coupon.domain;

import com.coupon.exception.BadRequestBusinessRuleException;
import com.coupon.exception.ConflictBusinessRuleException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 6)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;

    @Column(nullable = false)
    private Boolean published;

    @Column(nullable = false)
    private boolean redeemed;

    public Coupon(String code,
                  String description,
                  BigDecimal discountValue,
                  LocalDateTime expirationDate,
                  boolean published) {

        this.code = sanitizeCode(code);
        this.description = description;
        this.discountValue = validateDiscount(discountValue);
        this.expirationDate = validateExpiration(expirationDate);
        this.published = published;
        this.status = CouponStatus.ACTIVE;
        this.redeemed = false;
    }

    private String sanitizeCode(String rawCode) {
        if (rawCode == null || rawCode.isBlank()) {
            throw new BadRequestBusinessRuleException("O campo code não pode ser nulo ou vazio");
        }
        String sanitized = rawCode
                .replaceAll("[^a-zA-Z0-9]", "")
                .toUpperCase();

        if (sanitized.length() != 6) {
            throw new BadRequestBusinessRuleException(
                    "O campo code deve conter exatamente 6 caracteres alfanuméricos alem dos caracteres especiais.");
        }

        return sanitized;
    }

    private BigDecimal validateDiscount(BigDecimal value) {
        if (value.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new BadRequestBusinessRuleException(
                    "O campo  discountValue deve possuir o valor mínimo de 0.5");
        }
        return value;
    }

    private LocalDateTime validateExpiration(LocalDateTime date) {
        if (date.isBefore(LocalDateTime.now())) {
            throw new BadRequestBusinessRuleException(
                    "O campo expirationDate deve ser uma data futura");
        }
        return date;
    }

    public void delete() {
        if (this.status == CouponStatus.DELETED) {
            throw new ConflictBusinessRuleException("Cupom já deletado");
        }
        this.status = CouponStatus.DELETED;
    }
}
