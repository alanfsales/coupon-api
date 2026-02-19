package com.coupon.service;

import com.coupon.domain.Coupon;
import com.coupon.dto.CouponDTO;
import com.coupon.dto.CouponResponseDTO;
import com.coupon.exception.ResourceNotFoundException;
import com.coupon.mapper.CouponMapper;
import com.coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CouponResponseDTO create(CouponDTO dto){
        Coupon coupon = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(coupon));
    }

    public CouponResponseDTO findById(UUID id){
        return mapper.toResponse(getCouponOrThrow(id));
    }

    @Transactional
    public void deleteById(UUID id){
        Coupon coupon = getCouponOrThrow(id);
        coupon.delete();
    }

    private Coupon getCouponOrThrow(UUID id){
        return repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Cupom não encontrado"));

    }
}
