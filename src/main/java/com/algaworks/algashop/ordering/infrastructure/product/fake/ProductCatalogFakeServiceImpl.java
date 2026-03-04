package com.algaworks.algashop.ordering.infrastructure.product.fake;

import com.algaworks.algashop.ordering.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.common.Money;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.util.Optional;
//@Component
public class ProductCatalogFakeServiceImpl implements ProductCatalogService {
    @Override
    public Optional<Product> ofId(ProductId productId) {
        return Optional.of(Product.builder()
                .id(new ProductId())
                .inStock(true)
                .name(new ProductName("Notebook X11"))
                .price(new Money("3000")).build());
    }
}
