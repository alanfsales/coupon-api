package com.coupon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictBusinessRuleException extends ResponseStatusException {

    public ConflictBusinessRuleException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
