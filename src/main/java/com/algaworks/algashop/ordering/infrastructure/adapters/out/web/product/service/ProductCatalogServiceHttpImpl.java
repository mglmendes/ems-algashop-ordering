package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.service;

import com.algaworks.algashop.ordering.core.domain.model.common.Money;
import com.algaworks.algashop.ordering.core.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductName;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.client.ProductCatalogAPIClient;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.response.ProductResponse;
import com.algaworks.algashop.ordering.infrastructure.config.exceptionhandler.BadGatewayException;
import com.algaworks.algashop.ordering.infrastructure.config.exceptionhandler.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.net.SocketTimeoutException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductCatalogServiceHttpImpl implements ProductCatalogService {

    private final ProductCatalogAPIClient productCatalogAPIClient;

    @Retryable(
            maxRetries = 3,
            delayString = "3s",
            multiplier = 2,
            includes = { GatewayTimeoutException.class, BadGatewayException.class}
    )
    @Override
    public Optional<Product> ofId(ProductId productId) {
        ProductResponse productResponse;
        log.info("Loading Product {}", productId);
        try {
            productResponse = productCatalogAPIClient.getById(productId.value());
        } catch (ResourceAccessException e) {
            throw new GatewayTimeoutException("Product Catalog API Timeout - Resource Access Exception", e);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (RestClientException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new GatewayTimeoutException("Product Catalog API Timeout - SocketTimeoutException", e);
            }
            throw new BadGatewayException("Product Catalog API Bad Gateway - RestClientException", e);
        }
        return Optional.of(
                Product.builder()
                        .id(new ProductId(productResponse.getId()))
                        .name(new ProductName(productResponse.getName()))
                        .inStock(productResponse.getInStock())
                        .price(new Money(productResponse.getSalePrice()))
                        .build()
        );
    }
}
