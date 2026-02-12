package com.algaworks.algashop.ordering.domain.model.product.service;

import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;

import java.util.Optional;

public interface ProductCatalogService {

    Optional<Product> ofId(ProductId productId);
}
