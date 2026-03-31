package com.algaworks.algashop.ordering.core.domain.model.customer.exception;

import com.algaworks.algashop.ordering.core.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.core.domain.model.generic.ErrorMessages;

public class CustomerAlreadyHaveShoppingCartException extends DomainException {

    public CustomerAlreadyHaveShoppingCartException() {
        super(ErrorMessages.ERROR_CUSTOMER_ALREADY_HAVE_SHOPPING_CART);
    }
}
