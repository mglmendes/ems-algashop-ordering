package com.algaworks.algashop.ordering.core.domain.model.product.exception;

import com.algaworks.algashop.ordering.core.domain.model.generic.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.generic.ErrorMessages;

import java.util.UUID;

public class ProductNotFoundException extends DomainEntityNotFoundException {
    public ProductNotFoundException(UUID productId) {
        super(String.format(
                ErrorMessages.ERROR_PRODUCT_NOT_FOUND_EXCEPTION,
                productId
        ));
    }
}
