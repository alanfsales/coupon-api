package com.coupon.controller;

import com.coupon.domain.Coupon;
import com.coupon.dto.CouponDTO;
import com.coupon.service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CouponController.class)
@AutoConfigureMockMvc
class CouponControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CouponService service;

    private Coupon coupon;
    private CouponDTO couponDTO;

    @BeforeEach
    void setUp(){
        couponDTO = new CouponDTO("ABC-123", "Descrição do cupom", new BigDecimal("0.8"),
                LocalDateTime.now().plusDays(10), false);
        coupon = new Coupon("ABC-123", "Descrição do cupom", new BigDecimal("0.8"),
                LocalDateTime.now().plusDays(10),false);
    }

    @Test
    void deveCriarCupomComSucesso() throws Exception {
        given(service.save(Mockito.any())).willReturn(coupon);

        mvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(couponDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("ABC123"));

        then(service).should().save(Mockito.any());
    }

    @Test
    void deveRetornarCupomQuandoExistir() throws Exception {
        UUID id = UUID.randomUUID();

        given(service.findById(id)).willReturn(coupon);

        mvc.perform(get("/coupon/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ABC123"))
                .andExpect(jsonPath("$.description").value("Descrição do cupom"));

        then(service).should().findById(id);
    }


    @Test
    void deveRetornar404QuandoCupomNaoExistir() throws Exception {
        UUID id = UUID.randomUUID();

        given(service.findById(id))
                .willThrow(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cupom não encontrado"
                ));

        mvc.perform(get("/coupon/{id}", id))
                .andExpect(status().isNotFound());

        then(service).should().findById(id);
    }

    @Test
    void deveRetornar204QuandoDeletarComSucesso() throws Exception {
        UUID id = UUID.randomUUID();

        willDoNothing()
                .given(service)
                .delete(id);

        mvc.perform(delete("/coupon/{id}", id))
                .andExpect(status().isNoContent());

        then(service).should().delete(id);
    }

    @Test
    void deveRetornarBadRequestQuandoValidacaoFalhar() throws Exception {

        String json = """
        {
            "code": "",
            "description": "",
            "discountValue": 0.1,
            "expirationDate": "2023-01-01T00:00:00",
            "published": false
        }
        """;

        mvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").exists());
    }

    @Test
    void deveRetornarConflictQuandoViolacaoDeUnicidade() throws Exception {

        String json = """
        {
            "code": "ABC123",
            "description": "Descricao do cupom",
            "discountValue": 1.0,
            "expirationDate": "2030-01-01T00:00:00",
            "published": false
        }
        """;

        Mockito.when(service.save(Mockito.any()))
                .thenThrow(new DataIntegrityViolationException("erro"));

        mvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflict"))
                .andExpect(jsonPath("$.detail")
                        .value("Já existe um cupom com esse código."));
    }
}