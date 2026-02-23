package com.algaworks.algashop.ordering.domain.model.product.exception;

import com.algaworks.algashop.ordering.domain.model.generic.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;

import java.util.UUID;

public class ProductNotFoundException extends DomainEntityNotFoundException {
    public ProductNotFoundException(UUID productId) {
        super(String.format(
                ErrorMessages.ERROR_PRODUCT_NOT_FOUND_EXCEPTION,
                productId
        ));
    }
}
