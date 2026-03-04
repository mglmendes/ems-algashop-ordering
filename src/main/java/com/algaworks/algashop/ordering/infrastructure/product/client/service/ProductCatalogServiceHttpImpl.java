package com.algaworks.algashop.ordering.infrastructure.product.client.service;

import com.algaworks.algashop.ordering.domain.model.common.Money;
import com.algaworks.algashop.ordering.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductName;
import com.algaworks.algashop.ordering.infrastructure.product.client.ProductCatalogAPIClient;
import com.algaworks.algashop.ordering.infrastructure.product.client.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ProductCatalogServiceHttpImpl implements ProductCatalogService {

    private final ProductCatalogAPIClient productCatalogAPIClient;

    @Override
    public Optional<Product> ofId(ProductId productId) {
        ProductResponse productResponse = productCatalogAPIClient.getById(productId.value());
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
