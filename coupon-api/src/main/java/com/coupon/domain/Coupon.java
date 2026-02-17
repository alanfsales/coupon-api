package com.coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@Getter
@Setter
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
    private Boolean published = false;

    @Column(nullable = false)
    private Boolean redeemed = false;

    public Coupon(String code,
                  String description,
                  BigDecimal discountValue,
                  LocalDateTime expirationDate,
                  Boolean published) {

        this.code = sanitizeCode(code);
        this.description = description;
        this.discountValue = validateDiscount(discountValue);
        this.expirationDate = validateExpiration(expirationDate);
        this.published = Boolean.TRUE.equals(published);
        this.status = CouponStatus.ACTIVE;
    }

    private String sanitizeCode(String rawCode) {
        String sanitized = rawCode
                .replaceAll("[^a-zA-Z0-9]", "")
                .toUpperCase();

        if (sanitized.length() != 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O campo code deve conter exatamente 6 caracteres alfanuméricos alem dos caracteres especiais.");
        }

        return sanitized;
    }

    private BigDecimal validateDiscount(BigDecimal value) {
        if (value.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O campo  discountValue deve ser maior que 0.5");
        }
        return value;
    }

    private LocalDateTime validateExpiration(LocalDateTime date) {
        if (date.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O campo expirationDate deve ser uma data futura");
        }
        return date;
    }

    public void delete() {
        if (this.status == CouponStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cupom já deletado");
        }
        this.status = CouponStatus.DELETED;
    }
}
