package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.service;

import com.algaworks.algashop.ordering.core.domain.model.common.Money;
import com.algaworks.algashop.ordering.core.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductName;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.client.ResilientProductCatalogAPIClient;
import com.algaworks.algashop.ordering.infrastructure.config.exceptionhandler.BadGatewayException;
import com.algaworks.algashop.ordering.infrastructure.config.exceptionhandler.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductCatalogServiceHttpImpl implements ProductCatalogService {

    private final ResilientProductCatalogAPIClient resilientProductCatalogAPIClient;

    @SneakyThrows
    @ConcurrencyLimit(10)
    @Retryable(
            maxRetries = 3,
            delayString = "3s",
            multiplier = 2,
            includes = { GatewayTimeoutException.class, BadGatewayException.class}
    )
    @Override
    public Optional<Product> ofId(ProductId productId) {
        return resilientProductCatalogAPIClient.getById(productId.value()).map( productResponse ->
                Product.builder()
                        .id(new ProductId(productResponse.getId()))
                        .name(new ProductName(productResponse.getName()))
                        .inStock(productResponse.getInStock())
                        .price(new Money(productResponse.getSalePrice()))
                        .build()
        );
    }
}
