package com.coupon.service;

import com.coupon.domain.Coupon;
import com.coupon.domain.CouponStatus;
import com.coupon.dto.CouponDTO;
import com.coupon.dto.CouponResponseDTO;
import com.coupon.exception.ConflictBusinessRuleException;
import com.coupon.mapper.CouponMapper;
import com.coupon.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository repository;

    @Mock
    private CouponMapper mapper;

    @InjectMocks
    private CouponService service;

    private Coupon coupon;
    private CouponDTO couponDTO;
    private CouponResponseDTO responseDTO;

    @BeforeEach
    void setUp(){
        couponDTO = new CouponDTO("ABC-123", "Descrição do cupom", new BigDecimal("0.8"),
                LocalDateTime.now().plusDays(10), false);
        coupon = new Coupon("ABC-123", "Descrição do cupom", new BigDecimal("0.8"),
                LocalDateTime.now().plusDays(10),false);
        responseDTO = new CouponResponseDTO(UUID.fromString("b5aa498f-2f64-4c4a-827b-bef0c915acd5"),
                "ABC123", "Descrição do cupom", new BigDecimal("0.8"),
                LocalDateTime.now().plusDays(10), CouponStatus.ACTIVE,false, false);
    }

    @Test
    void deveSalvarCupomComSucesso() {
        given(mapper.toEntity(couponDTO)).willReturn(coupon);
        given(repository.save(coupon)).willReturn(coupon);
        given(mapper.toResponse(coupon)).willReturn(responseDTO);

        CouponResponseDTO result = service.create(couponDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);

        then(mapper).should().toEntity(couponDTO);
        then(repository).should().save(coupon);
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deveRetornarCupomQuandoExistir() {
        UUID id = UUID.randomUUID();

        given(repository.findById(id)).willReturn(Optional.of(coupon));
        given(mapper.toResponse(coupon)).willReturn(responseDTO);

        CouponResponseDTO result = service.findById(id);

        assertNotNull(result);
        assertEquals(responseDTO, result);

        then(repository).should().findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoCupomNaoExistir() {
        UUID id = UUID.randomUUID();

        given(repository.findById(id)).willReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.findById(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Cupom não encontrado", exception.getReason());

        then(repository).should().findById(id);
    }

    @Test
    void deveDeletarCupomComSucesso() {
        UUID id = UUID.randomUUID();

        given(repository.findById(id)).willReturn(Optional.of(coupon));

        service.deleteById(id);

        then(repository).should().findById(id);

        assertEquals(CouponStatus.DELETED, coupon.getStatus());
    }

    @Test
    void naoDeveDeletarCupomJaDeletado() {
        UUID id = UUID.randomUUID();
        coupon.delete();

        given(repository.findById(id)).willReturn(Optional.of(coupon));

        assertThrows(
                ConflictBusinessRuleException.class,
                () -> service.deleteById(id)
        );

        then(repository).should().findById(id);
    }

}
