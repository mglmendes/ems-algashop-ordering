package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.shipping.config;

import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.shipping.client.RapiDexAPIClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class RapiDexAPIClientConfig {

    @Bean
    public RapiDexAPIClient rapiDexAPIClient(
            RestClient.Builder restClientBuilder,
            @Value("${algashop.integrations.rapidex.url}") String rapiDexUrl
    ) {
        RestClient restClient = restClientBuilder
                .baseUrl(rapiDexUrl)
                .requestFactory(generateClintHttpRequestFactory())
                .build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return proxyFactory.createClient(RapiDexAPIClient.class);
    }

    private ClientHttpRequestFactory generateClintHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(7));
        factory.setConnectTimeout(Duration.ofSeconds(3));
        return factory;
    }
}
