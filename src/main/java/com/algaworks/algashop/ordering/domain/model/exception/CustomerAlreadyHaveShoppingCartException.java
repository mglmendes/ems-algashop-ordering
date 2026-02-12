package com.algaworks.algashop.ordering.domain.model.exception;

import java.util.UUID;

public class CustomerAlreadyHaveShoppingCartException extends DomainException{

    public CustomerAlreadyHaveShoppingCartException(UUID customerId) {
        super(String.format(
                ErrorMessages.ERROR_CUSTOMER_ALREADY_HAVE_SHOPPING_CART,
                customerId
        ));
    }
}
