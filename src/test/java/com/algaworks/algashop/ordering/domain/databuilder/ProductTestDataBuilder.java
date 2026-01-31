package com.algaworks.algashop.ordering.domain.databuilder;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;

public class ProductTestDataBuilder {

    private ProductTestDataBuilder() {
    }

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(new ProductId())
                .inStock(true)
                .productName(new ProductName("Notebook"))
                .price(new Money("3000"));
    }

    public static Product.ProductBuilder aProductUnavailable() {
        return Product
                .builder()
                .id(new ProductId())
                .productName(new ProductName("Desktop 9000"))
                .price(new Money("5000"))
                .inStock(false);
    }

    public static Product.ProductBuilder aProductAltRamMemory() {
        return Product
                .builder()
                .id(new ProductId())
                .productName(new ProductName("RAM Memory"))
                .inStock(true)
                .price(new Money("400"));
    }

    public static Product.ProductBuilder aProductAltMousePad() {
        return Product
                .builder()
                .id(new ProductId())
                .productName(new ProductName("Mouse Pad"))
                .inStock(true)
                .price(new Money("100"));
    }
}
