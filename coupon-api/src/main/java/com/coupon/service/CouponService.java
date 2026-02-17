package com.coupon.service;

import com.coupon.domain.Coupon;
import com.coupon.dto.CouponDTO;
import com.coupon.mapper.CouponMapper;
import com.coupon.repository.CouponRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository repository;

    private final CouponMapper mapper;

    public CouponService(CouponRepository repository, CouponMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public Coupon save(CouponDTO dto){
        Coupon coupon = mapper.toEntity(dto);
        return repository.save(coupon);
    }

    public Coupon findById(UUID id){
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cupom não encontrado"));
    }

    @Transactional
    public void delete(UUID id){
        Coupon coupon = findById(id);
        coupon.delete();
    }
}
