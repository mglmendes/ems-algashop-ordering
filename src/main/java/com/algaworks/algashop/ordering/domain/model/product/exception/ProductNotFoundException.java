package com.algaworks.algashop.ordering.domain.model.product.exception;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(ProductId productId) {
        super(String.format(
                ErrorMessages.ERROR_PRODUCT_NOT_FOUND_EXCEPTION,
                productId.value()
        ));
    }
}
