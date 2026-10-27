package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.config;

import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.client.ProductCatalogAPIClient;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class ProductCatalogAPIConfig {

    @Bean
    public ProductCatalogAPIClient productCatalogAPIClient(
            RestClient.Builder builder,
            ProductCatalogIntegrationProperties properties,
            OAuth2AuthorizedClientManager manager
    ) {

        var interceptor = new OAuth2ClientHttpRequestInterceptor(
                manager
        );

        interceptor.setClientRegistrationIdResolver(_ -> properties.getOauth2ClientRegistrationId());
        interceptor.setPrincipalResolver(_ -> generatePrincipalResolver(properties.getOauth2ClientRegistrationId()));

        RestClient restClient = builder
                .baseUrl(properties.getUrl())
                .requestFactory(generateClintHttpRequestFactory())
                .requestInterceptor(interceptor)
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFActory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFActory.createClient(ProductCatalogAPIClient.class);
    }

    private Authentication generatePrincipalResolver(String principalName) {
        return new AbstractAuthenticationToken(Collections.EMPTY_SET) {
            @Override
            public @Nullable Object getCredentials() {
                return null;
            }

            @Override
            public @Nullable Object getPrincipal() {
                return principalName;
            }
        };
    }

    private ClientHttpRequestFactory generateClintHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(7));
        factory.setConnectTimeout(Duration.ofSeconds(3));
        return factory;
    }
}
