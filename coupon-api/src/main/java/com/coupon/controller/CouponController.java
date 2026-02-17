package com.coupon.controller;

import com.coupon.domain.Coupon;
import com.coupon.dto.CouponDTO;
import com.coupon.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coupon save(@RequestBody @Valid CouponDTO dto) {
        return service.save(dto);
    }

    @GetMapping("/{id}")
    public Coupon findById(@PathVariable UUID id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id){
        service.delete(id);
    }
}
