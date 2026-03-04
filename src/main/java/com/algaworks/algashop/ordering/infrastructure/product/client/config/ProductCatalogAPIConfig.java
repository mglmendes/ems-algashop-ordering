package com.algaworks.algashop.ordering.infrastructure.product.client.config;

import com.algaworks.algashop.ordering.infrastructure.product.client.ProductCatalogAPIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProductCatalogAPIConfig {

    @Bean
    public ProductCatalogAPIClient productCatalogAPIClient(
            RestClient.Builder builder,
            @Value("${algashop.integrations.product-catalog.url}") String url
    ) {
        RestClient restClient = builder.baseUrl(url).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFActory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFActory.createClient(ProductCatalogAPIClient.class);
    }
}
