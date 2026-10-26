package com.algaworks.algashop.ordering.utils;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;

public class MockJwtDecoderConfig {

    @Primary
    @Bean
    public JwtDecoder jwtDecoder() {
        return MockJwtDecoderFactory.createMockJwtDecoder();
    }
}
