package com.algaworks.algashop.ordering.domain.model.customer.exception;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;

import java.util.UUID;

public class CustomerAlreadyHaveShoppingCartException extends DomainException {

    public CustomerAlreadyHaveShoppingCartException(UUID customerId) {
        super(String.format(
                ErrorMessages.ERROR_CUSTOMER_ALREADY_HAVE_SHOPPING_CART,
                customerId
        ));
    }
}
