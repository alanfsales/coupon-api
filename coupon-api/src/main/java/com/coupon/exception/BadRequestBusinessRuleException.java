package com.coupon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestBusinessRuleException extends ResponseStatusException {

    public BadRequestBusinessRuleException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
