package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.product.client.config;

import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.product.client.ProductCatalogAPIClient;
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
public class ProductCatalogAPIConfig {

    @Bean
    public ProductCatalogAPIClient productCatalogAPIClient(
            RestClient.Builder builder,
            @Value("${algashop.integrations.product-catalog.url}") String url
    ) {
        RestClient restClient = builder
                .baseUrl(url)
                .requestFactory(generateClintHttpRequestFactory())
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFActory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFActory.createClient(ProductCatalogAPIClient.class);
    }

    private ClientHttpRequestFactory generateClintHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(7));
        factory.setConnectTimeout(Duration.ofSeconds(3));
        return factory;
    }
}
