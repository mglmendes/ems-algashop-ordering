package com.algaworks.algashop.ordering.core.domain.model.product.service;

import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductId;

import java.util.Optional;

public interface ProductCatalogService {
    Optional<Product> ofId(ProductId productId);
}
