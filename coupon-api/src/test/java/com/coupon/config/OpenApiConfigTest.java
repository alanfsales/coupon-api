package com.coupon.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenApiConfigTest {

    @Test
    void deveCriarOpenApiComInformacoesCorretas() {

        OpenApiConfig config = new OpenApiConfig();

        OpenAPI openAPI = config.customOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals(
                "Api Coupon - Desafio técnico Alan Sales",
                openAPI.getInfo().getTitle()
        );
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertEquals(
                "API de gerenciamento de cupom",
                openAPI.getInfo().getDescription()
        );
    }
}
