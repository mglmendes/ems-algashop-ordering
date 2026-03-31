package com.algaworks.algashop.ordering.core.domain.model.shoppingcart.exception;

import com.algaworks.algashop.ordering.core.domain.model.generic.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.generic.ErrorMessages;

import java.util.UUID;

public class ShoppingCartNotFoundException extends DomainEntityNotFoundException {
    public ShoppingCartNotFoundException(UUID shoppingCartId) {
       super(String.format(
               ErrorMessages.ERROR_SHOPPING_CART_NOT_FOUND,
               shoppingCartId
       ));
    }

    public ShoppingCartNotFoundException() {
        super(ErrorMessages.ERROR_SHOPPING_CART_NOT_FOUND_FOR_CUSTOMER);
    }
}
